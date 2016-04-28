using System;
using Alchemy;
using Alchemy.Classes;
using Newtonsoft.Json.Linq;
using System.Threading;
using System.Reflection;

namespace SmallModel
{
	class GraphWalkerClientWorker
	{
		private WebSocketClient ws = null;
		public AutoResetEvent connectedEvent = new AutoResetEvent(false);
		public AutoResetEvent loadModelEvent = new AutoResetEvent(false);
		public AutoResetEvent startEvent = new AutoResetEvent(false);
		public AutoResetEvent hasNextEvent = new AutoResetEvent(false);
		public AutoResetEvent getNextEvent = new AutoResetEvent(false);
		public AutoResetEvent getDataEvent = new AutoResetEvent(false);
		private volatile string element = "";
		private volatile JObject data;
		public volatile bool isHasNext = false;

		public void connect ()
		{
			Console.WriteLine ("Will connect to GraphWalker server...");
			ws = new WebSocketClient ("ws://localhost:8887/") {
				OnReceive = OnReceive,
				OnConnected = OnConnected
			};
			ws.Connect ();
		}

		public void disconnect ()
		{
			Console.WriteLine ("Will disconnect from GraphWalker server...");
			ws.Disconnect ();
			WebSocketClient.Shutdown ();
			Console.WriteLine ("Disconnected!");
		}

		void OnConnected(UserContext context) {
			Console.WriteLine ("Connected!");
			connectedEvent.Set ();
		}

		void OnReceive (UserContext context)
		{
			Console.WriteLine ("Receiving message: " + context.DataFrame.ToString ());
			JObject response = JObject.Parse (context.DataFrame.ToString ());

			if (response.GetValue("command") == null) {
				Console.WriteLine ("Unknown response type: " + response.ToString ());
				return;
			}

			if (response.GetValue ("command").ToString ().Equals ("start")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("Failed start the GraphWalker machine: " + response.ToString ());
					return;
				}
				Console.WriteLine ("GraphWalker machine started ok");
				startEvent.Set ();
			} else if (response.GetValue ("command").ToString ().Equals ("hasNext")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("hasNext failed");
					isHasNext = false;
					hasNextEvent.Set ();
					return;
				}
				if (!response.GetValue ("hasNext").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("hasNext returned false");
					isHasNext = false;
					hasNextEvent.Set ();
					return;
				}

				Console.WriteLine ("hasNext returned true");
				isHasNext = true;
				hasNextEvent.Set ();
			} else if (response.GetValue ("command").ToString ().Equals ("getNext")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("getNext returned false");
					return;
				}
				Console.WriteLine ("getNext: " + response.GetValue("name").ToString());
				element = response.GetValue ("name").ToString ();
				getNextEvent.Set ();
			} else if (response.GetValue ("command").ToString ().Equals ("getData")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("getData failed");
					return;
				}
				data = (JObject)response.DeepClone();
				getDataEvent.Set ();
			} else {
				Console.WriteLine ("Unknown response command: " + response.ToString ());
			}

		}

		public JObject getDataObject ()
		{
			getDataEvent.Reset ();
			return (JObject)data.DeepClone();
		}

		public string getMessage ()
		{
			hasNextEvent.Reset ();
			getNextEvent.Reset ();
			return (string)element.Clone();
		}

		public void hasNext ()
		{
			ws.Send (@"{
				""command"":""hasNext""
				}");
		}

		public void getNext ()
		{
			ws.Send (@"{
				""command"":""getNext""
				}");
		}

		public void start (string model)
		{
			ws.Send (model);
		}

		public void getData ()
		{
			ws.Send (@"{
				""command"":""getData""
				}");
		}
	}

	class MainClass
	{
		private const string model =
			@"{
			  ""command"": ""start"",
			  ""gw3"": {
			  ""name"": ""A small test model"",
			  ""models"": [
			    {
			      ""name"": ""Small model"",
			      ""generator"": ""random(edge_coverage(100))"",
			      ""startElementId"": ""e0"",
			      ""vertices"": [
			        {
			          ""name"": ""v_VerifySomeAction"",
			          ""id"": ""n0""
			        },
			        {
			          ""name"": ""v_VerifySomeOtherAction"",
			          ""id"": ""n1""
			        }
			      ],
			      ""edges"": [
			        {
			          ""name"": ""e_FirstAction"",
			          ""id"": ""e0"",
			          ""targetVertexId"": ""n0""
			        },
			        {
			          ""name"": ""e_AnotherAction"",
			          ""id"": ""e1"",
			          ""sourceVertexId"": ""n0"",
			          ""targetVertexId"": ""n1""
			        },
			        {
			          ""name"": ""e_SomeOtherAction"",
			          ""id"": ""e2"",
			          ""sourceVertexId"": ""n1"",
			          ""targetVertexId"": ""n1""
			        },
			        {
			          ""name"": ""e_SomeOtherAction"",
			          ""id"": ""e3"",
			          ""sourceVertexId"": ""n1"",
			          ""targetVertexId"": ""n0""
			        }
			      ]
			    }
			  ]
			}
		}";

		public static void Main (string[] args)
		{
			MainClass program = new MainClass ();
			program.run ();
		}

		public void run ()
		{
			// Create the thread object, passing in the
			// GraphWalkerClientWorker.connect method via a ThreadStart delegate.
			// This does not start the thread.
			GraphWalkerClientWorker worker = new GraphWalkerClientWorker ();
			Thread workerThread = new Thread (worker.connect);

			// Start the thread
			workerThread.Start ();

			// Spin for a while waiting for the started thread to become
			// alive:
			while (!workerThread.IsAlive)
				;

			worker.connectedEvent.WaitOne ();

			worker.start (model);
			worker.startEvent.WaitOne ();

			Type smallModelType = typeof(SmallModel);
			ConstructorInfo ctor = smallModelType.GetConstructor(System.Type.EmptyTypes);

			while (true) {
				worker.hasNext ();
				worker.hasNextEvent.WaitOne ();
				if (!worker.isHasNext)
					break;

				worker.getNext ();
				worker.getNextEvent.WaitOne ();
				string methodName = (string)worker.getMessage();

				object instance = ctor.Invoke(null);
				MethodInfo methodInfo = smallModelType.GetMethod(methodName);
				methodInfo.Invoke(instance, new object[]{});

				worker.getData ();
				worker.getDataEvent.WaitOne ();
				Console.WriteLine("Data: " + worker.getDataObject().ToString());
			}
            worker.disconnect();
			workerThread.Join ();
		}

	}
}
