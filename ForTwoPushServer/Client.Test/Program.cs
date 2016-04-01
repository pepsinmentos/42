using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;
using Microsoft.AspNet.SignalR.Client;

namespace Client.Test
{
    internal class Program
    {
        private static int userId;
        private static int recipientId;

        private static void Main(string[] args)
        {
            try
            {
                Console.WriteLine("Please enter your UserId: ");
                userId = Int32.Parse(Console.ReadLine());
                Console.WriteLine("Please enter RecipientId: ");
                recipientId = Int32.Parse(Console.ReadLine());

                var hubUrl = ConfigurationManager.AppSettings["hub_url"];
                var hubConnection = new HubConnection(hubUrl);
                hubConnection.Headers.Add("UserId", userId.ToString());
                IHubProxy chatHub = hubConnection.CreateHubProxy("ChatHub");
                chatHub.On<string>("addMessage", message => { WriteChatToClient(new ChatLine() { Message = message }); });
                hubConnection.Start().Wait();

                string s = string.Empty;

                var unreadMessages = chatHub.Invoke<IEnumerable<ChatLine>>("GetUnreadMessages").Result;
                foreach (var unreadMessage in unreadMessages)
                {
                    WriteChatToClient(unreadMessage);
                }

                while (s != "stop")
                {
                    s = NextMessage();
                    chatHub.Invoke("Send", new ChatLine {Message = s, SenderId = userId, RecipientId = recipientId});
                }

                Console.ReadLine();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
                Console.ReadKey();
            }
        }

        public static string NextMessage()
        {
            return Console.ReadLine();
        }

        public static void WriteChatToClient(ChatLine chat)
        {
            Console.Write(chat.SenderId + ": ");
            Console.WriteLine(chat.Message);
        }
    }
}