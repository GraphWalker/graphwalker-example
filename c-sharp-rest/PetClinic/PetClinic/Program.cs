using System;
using System.IO;
using RestSharp;
using Newtonsoft.Json.Linq;
using System.Reflection;

namespace PetClinic
{
	class GraphWalkerRestClient
	{
		private RestClient client = new RestClient();
		private RestRequest requestHasNext = new RestRequest("hasNext", Method.GET);
		private RestRequest requestGetNext = new RestRequest("getNext", Method.GET);
		private RestRequest requestGetData = new RestRequest("getData", Method.GET);
		private RestRequest requestLoad = new RestRequest("load", Method.POST);
		private RestRequest requestGetStatistics = new RestRequest("getStatistics", Method.GET);

		public GraphWalkerRestClient()
		{
			client.BaseUrl = new Uri("http://localhost:8887/graphwalker/");
			requestHasNext.AddHeader("Accept", "text/plain");
			requestGetNext.AddHeader("Accept", "text/plain");
			requestGetData.AddHeader("Accept", "text/plain");
			requestLoad.AddHeader("Accept", "text/plain");
			requestGetStatistics.AddHeader("Accept", "text/plain");
		}

		public bool hasNext()
		{
			IRestResponse restResponse = client.Execute(requestHasNext);
			checkError (restResponse);

			JObject jsonResponse = JObject.Parse (restResponse.Content.ToString ());
			if (!jsonResponse.GetValue ("result").ToString().Equals("ok", StringComparison.CurrentCultureIgnoreCase))
				return false;

			if (!jsonResponse.GetValue ("hasNext").ToString().Equals("true", StringComparison.CurrentCultureIgnoreCase))
				return false;
			return true;
		}

		public JObject getNext()
		{
			IRestResponse restResponse = client.Execute(requestGetNext);
			checkError (restResponse);
			return JObject.Parse (restResponse.Content.ToString ());
		}

		public string getData()
		{
			IRestResponse restResponse = client.Execute(requestGetData);
			checkError (restResponse);
			JObject jsonResponse = JObject.Parse (restResponse.Content.ToString ());
			return jsonResponse.GetValue("data").DeepClone().ToString();
		}

		public void load(string model)
		{
			string data = File.ReadAllText(model);
			requestLoad.AddHeader ("Content-Type", "text/plain");
			requestLoad.AddParameter("text/plain", data, ParameterType.RequestBody);
			IRestResponse restResponse = client.Execute(requestLoad);
			checkError (restResponse);
		}

		public string getStatistics()
		{
			IRestResponse restResponse = client.Execute(requestGetStatistics);
			checkError (restResponse);
			JObject jsonResponse = JObject.Parse (restResponse.Content.ToString ());
			return jsonResponse.ToString();
		}

		private void checkError(IRestResponse response)
		{
			if (response.ErrorException != null)
			{
				const string message = "Error retrieving response. Check inner details for more info.";
				throw new Exception(message, response.ErrorException);
			}

			int numericStatusCode = (int)response.StatusCode;
			if (numericStatusCode != 200)
			{
				const string message = "Error retrieving response. Check inner details for more info.";
				throw new Exception(message, response.ErrorException);
			}

			JObject jsonResponse = JObject.Parse (response.Content.ToString ());
			if (!jsonResponse.GetValue ("result").ToString().Equals("ok", StringComparison.CurrentCultureIgnoreCase))
			{
				const string message = "REST call failed. Check inner details for more info.";
				throw new Exception(message);
			}
		}
	}

	class MainClass
	{
		public static void Main (string[] args)
		{
			MainClass program = new MainClass ();
			program.run ();
		}

		private void run ()
		{
			GraphWalkerRestClient gwRestClient = new GraphWalkerRestClient ();
			gwRestClient.load ("../../../PetClinic.gw3");

			// As long as we have elemnts from GraphWalkers path generation
			// to fetch, we'll continue 
			while (gwRestClient.hasNext()) 
			{
				// Get the next element name from GraphWalker.
				// The element might either be an edge or a vertex.
				JObject nextStep = gwRestClient.getNext();

				Type type = Type.GetType("PetClinic." + nextStep.GetValue("modelName").ToString());
				//Type type = Type.GetType("PetClinic.PetClinicSharedState");
				ConstructorInfo ctor = type.GetConstructor(System.Type.EmptyTypes);

				// Invoke a method call on
				if (nextStep.GetValue ("currentElementName") != null) 
				{
					object instance = ctor.Invoke(null);
					MethodInfo methodInfo = type.GetMethod(nextStep.GetValue("currentElementName").ToString());
					methodInfo.Invoke(instance, new object[]{});
				}

				// If any data, write it to the terminal.
				Console.WriteLine (gwRestClient.getData ());
			}

			// Get the statistics from the test
			Console.WriteLine (gwRestClient.getStatistics() );
		}
	}
}
