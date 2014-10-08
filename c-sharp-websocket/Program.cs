using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.WebSockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace WSChatClient
{
    class Program
    {
        private static async Task ChatWithServer()
        {
            using (ClientWebSocket ws = new ClientWebSocket())
            {
                Uri serverUri = new Uri("ws://localhost/WSChat/WSHandler.ashx");
                await ws.ConnectAsync(serverUri, CancellationToken.None);
                while (true)
                {
                    Console.Write("Input message ('exit' to exit): ");
                    string msg = Console.ReadLine();
                    if (msg == "exit")
                    {
                        break;
                    }
                    ArraySegment<byte> bytesToSend = new ArraySegment<byte>(
                        Encoding.UTF8.GetBytes(msg));
                    await ws.SendAsync(bytesToSend, WebSocketMessageType.Text, true, CancellationToken.None);
                    ArraySegment<byte> bytesReceived = new ArraySegment<byte>(new byte[1024]);
                    WebSocketReceiveResult result = await ws.ReceiveAsync(
                        bytesReceived, CancellationToken.None);
                    Console.WriteLine(Encoding.UTF8.GetString(bytesReceived.Array, 0, result.Count));
                    if (ws.State != WebSocketState.Open)
                    {
                        break;
                    }
                }
            }
        }

        static void Main(string[] args)
        {
            Task t = ChatWithServer();
            t.Wait();
        }
    }
}
