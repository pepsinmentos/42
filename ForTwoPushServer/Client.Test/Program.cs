using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;
using Microsoft.AspNet.SignalR.Client;

namespace Client.Test
{
    class Program
    {
        static int userId;
        static int recipientId;
        static void Main(string[] args)
        {
            Console.WriteLine("Please enter your UserId: ");
            userId = Int32.Parse(Console.ReadLine());
            Console.WriteLine("Please enter RecipientId: ");
            recipientId = Int32.Parse(Console.ReadLine());


            var hubConnection = new HubConnection("http://localhost:8080");
            hubConnection.Headers.Add("UserId", userId.ToString());
            IHubProxy chatHub = hubConnection.CreateHubProxy("ChatHub");
            chatHub.On<ChatLine>("addMessage", message => 
            {
                WriteChatToClient(message);
            });
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
                chatHub.Invoke("Send", new ChatLine { Message = s, SenderId = userId, RecipientId = recipientId });
            }

            Console.ReadLine();
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
