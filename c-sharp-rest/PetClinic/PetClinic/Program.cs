using System;
using System.IO;
using RestSharp;
using Newtonsoft.Json.Linq;
using System.Reflection;

namespace PetClinic
{
	static class GraphWalkerRestClient
	{
		private static RestClient client = new RestClient();
		private static RestRequest requestHasNext = new RestRequest("hasNext", Method.GET);
		private static RestRequest requestGetNext = new RestRequest("getNext", Method.GET);
        private static RestRequest requestGetData = new RestRequest("getData", Method.GET);
        private static RestRequest requestLoad = new RestRequest("load", Method.POST);
		private static RestRequest requestGetStatistics = new RestRequest("getStatistics", Method.GET);

        static GraphWalkerRestClient()
		{
			client.BaseUrl = new Uri("http://localhost:8887/graphwalker/");
			requestHasNext.AddHeader("Accept", "text/plain");
			requestGetNext.AddHeader("Accept", "text/plain");
            requestGetData.AddHeader("Accept", "text/plain");
            requestLoad.AddHeader("Accept", "text/plain");
			requestGetStatistics.AddHeader("Accept", "text/plain");
		}

		public static bool hasNext()
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

		public static JObject getNext()
		{
			IRestResponse restResponse = client.Execute(requestGetNext);
			checkError (restResponse);
			return JObject.Parse (restResponse.Content.ToString ());
		}

        public static JObject getData()
        {
            IRestResponse restResponse = client.Execute(requestGetData);
            checkError(restResponse);
            JObject jsonResponse = JObject.Parse(restResponse.Content.ToString());
            return JObject.Parse(jsonResponse.GetValue("data").DeepClone().ToString());
        }

        public static void setData( string str )
        {
            // The request has to be built uniquely for every call, otherwise
            // the AddParameter will be the same for every call.
            RestRequest requestSetData = new RestRequest("setData/{script}", Method.PUT);
            requestSetData.AddHeader("Accept", "text/plain");
            requestSetData.AddHeader("Content-Type", "text/plain");
			requestSetData.AddParameter("script", str, ParameterType.UrlSegment);
            IRestResponse restResponse = client.Execute(requestSetData);
            checkError(restResponse);
        }

        public static void load(string model)
		{
			string data = File.ReadAllText(model);
			requestLoad.AddHeader ("Content-Type", "text/plain");
			requestLoad.AddParameter("text/plain", data, ParameterType.RequestBody);
			IRestResponse restResponse = client.Execute(requestLoad);
			checkError (restResponse);
		}

		public static string getStatistics()
		{
			IRestResponse restResponse = client.Execute(requestGetStatistics);
			checkError (restResponse);
			JObject jsonResponse = JObject.Parse (restResponse.Content.ToString ());
			return jsonResponse.ToString();
		}

		private static void checkError(IRestResponse response)
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
            GraphWalkerRestClient.load ("../../../PetClinic.gw3");

			// As long as we have elemnts from GraphWalkers path generation
			// to fetch, we'll continue 
			while (GraphWalkerRestClient.hasNext()) 
			{
				// Get the next element name from GraphWalker.
				// The element might either be an edge or a vertex.
				JObject nextStep = GraphWalkerRestClient.getNext();

                // Create a mapping from the model name to an actual class.
                Type type = Type.GetType("PetClinic." + nextStep.GetValue("modelName").ToString());
				ConstructorInfo ctor = type.GetConstructor(System.Type.EmptyTypes);

                // Invoke a method to call. If the currentElementName is null,
                // it means that it's an edge with no name. In practicality, this is a noop, a no operation.
                // No method to call, so we should move on to next step.
				if (nextStep.GetValue ("currentElementName") != null) 
				{
                    Console.WriteLine("Model and element to be called: " +
                        nextStep.GetValue("modelName").ToString()
                        + "." +
                        nextStep.GetValue("currentElementName").ToString());

                    object instance = ctor.Invoke(null);

                    // Create a mapping from the element name to an actual method.
                    MethodInfo methodInfo = type.GetMethod(nextStep.GetValue("currentElementName").ToString());
					methodInfo.Invoke(instance, new object[]{});
				}
			}

			// Get the statistics from the test
			Console.WriteLine (GraphWalkerRestClient.getStatistics() );
		}
	}
}
