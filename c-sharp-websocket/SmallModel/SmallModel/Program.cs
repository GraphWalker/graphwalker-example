using System;
using Alchemy;
using Alchemy.Classes;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using System.Threading;
using System.Reflection;

namespace SmallModel
{
	class GraphWalkerClientWorker
	{
		private WebSocketClient.ReadyStates state = WebSocketClient.ReadyStates.CLOSED;
		private WebSocketClient ws = null;
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
				OnReceive = OnReceive
			};
			ws.Connect ();
			Console.WriteLine ("Connected!");
			state = WebSocketClient.ReadyStates.OPEN;
		}

		public void disconnect ()
		{
			state = WebSocketClient.ReadyStates.CLOSED;
			Console.WriteLine ("Will disconnect from GraphWalker server...");
			ws.Disconnect ();
			Console.WriteLine ("Disconnected!");
		}

		void OnReceive (UserContext context)
		{
			Console.WriteLine ("Receiving message: " + context.DataFrame.ToString ());
			JObject response = JObject.Parse (context.DataFrame.ToString ());

			if (response.GetValue("type") == null) {
				Console.WriteLine ("Unknown response type: " + response.ToString ());
				return;
			}

			if (response.GetValue ("type").ToString ().Equals ("loadModel")) {
				if (!response.GetValue ("success").ToString().Equals( "true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("Failed loading the model: " + response.ToString ());
					return;
				}
				Console.WriteLine ("Model loaded ok");
				loadModelEvent.Set ();
			} else if (response.GetValue ("type").ToString ().Equals ("start")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("Failed start the GraphWalker machine: " + response.ToString ());
					return;
				}
				Console.WriteLine ("GraphWalker machine started ok");
				startEvent.Set ();
			} else if (response.GetValue ("type").ToString ().Equals ("hasNext")) {
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
			} else if (response.GetValue ("type").ToString ().Equals ("getNext")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("getNext returned false");
					return;
				}
				Console.WriteLine ("getNext: " + response.GetValue("name").ToString());
				element = response.GetValue ("name").ToString ();
				getNextEvent.Set ();
			} else if (response.GetValue ("type").ToString ().Equals ("getData")) {
				if (!response.GetValue ("success").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase)) {
					Console.WriteLine ("getData failed");
					return;
				}
				data = (JObject)response.DeepClone();
				getDataEvent.Set ();
			} else {
				Console.WriteLine ("Unknown response type: " + response.ToString ());
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

		public bool isConnected ()
		{
			return state == WebSocketClient.ReadyStates.OPEN;
		}

		public void hasNext ()
		{
			ws.Send (@"{
				""type"":""hasNext""
				}");
		}

		public void getNext ()
		{
			ws.Send (@"{
				""type"":""getNext""
				}");
		}

		public void start ()
		{
			ws.Send (@"{
				""type"":""start""
				}");
		}

		public void restart ()
		{
			ws.Send (@"{
				""type"":""restart""
				}");
		}

		public void getData ()
		{
			ws.Send (@"{
				""type"":""getData""
				}");
		}

		public void loadModel (string model)
		{
			ws.Send (model);
		}
	}

	class MainClass
	{
		private const string model =
			@"{
				""type"": ""loadModel"",
				""model"": {
					""name"": ""Small model"",
					""generator"": ""random(edge_coverage(100))"",
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
							""dstVertexId"": ""n0"",
							""startElement"": ""true"",
							""actions"": [
								{
									""action"": ""x=0;""
								},
								{
									""action"": ""y=0;""
								}
							]
						},
						{
							""name"": ""e_AnotherAction"",
							""id"": ""e1"",
							""srcVertexId"": ""n0"",
							""dstVertexId"": ""n1"",
							""actions"": [
								{
									""action"": ""y+=1;""
								}
							]
						},
						{
							""name"": ""e_SomeOtherAction"",
							""id"": ""e2"",
							""srcVertexId"": ""n1"",
							""dstVertexId"": ""n1"",
							""actions"": [
								{
									""action"": ""x+=1;""
								}
							]
						},
						{
							""name"": ""e_SomeOtherAction"",
							""id"": ""e3"",
							""srcVertexId"": ""n1"",
							""dstVertexId"": ""n0"",
							""actions"": [
								{
									""action"": ""y+=1;""
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

			// Waint until we're connected
			while (!worker.isConnected ()) {
				Console.WriteLine ("Waiting to connect...");
				Thread.Sleep (100);
			}

			worker.loadModel (model);
			worker.loadModelEvent.WaitOne ();

			worker.start ();
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
		}

	}
}
