using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ForTwo.Interop;
using ForTwo.Services.Interfaces;
using Microsoft.AspNet.SignalR;

namespace ForTwo.Hubs
{
    public class ChatHub : Hub
    {
        private Dictionary<int, string> ConnectionUserId;
        private IChatService ChatService;

        public ChatHub()
        {
            
        }
        public ChatHub(Dictionary<int, string> connectionUserId, IChatService chatService)
        {
            ConnectionUserId = connectionUserId;
            ChatService = chatService;
        }

        #region Overrides

        public override Task OnConnected()
        {
            var userId = GetUserIdFromHeaders();
            if (!ConnectionUserId.ContainsKey(userId))
            {
                ConnectionUserId.Add(userId, Context.ConnectionId);
            }
            else
            {
                if (ConnectionUserId[userId] != Context.ConnectionId)
                {
                    ConnectionUserId.Remove(userId);
                    ConnectionUserId.Add(userId, Context.ConnectionId);
                }
            }
            Console.WriteLine("UserId: {0} connected", userId);
            return base.OnConnected();
        }

        public override Task OnDisconnected(bool stopCalled)
        {
            if (ConnectionUserId.ContainsKey(GetUserIdFromHeaders()))
            {
                ConnectionUserId.Remove(GetUserIdFromHeaders());
            }
            Console.WriteLine("UserId: {0} disconnected", GetUserIdFromHeaders());
            return base.OnDisconnected(stopCalled);
        }

        public override Task OnReconnected()
        {
            var userId = GetUserIdFromHeaders();
            if (!ConnectionUserId.ContainsKey(userId))
            {
                ConnectionUserId.Add(userId, Context.ConnectionId);
            }
            else
            {
                if (ConnectionUserId[userId] != Context.ConnectionId)
                {
                    ConnectionUserId.Remove(userId);
                    ConnectionUserId.Add(userId, Context.ConnectionId);
                }
            }
            Console.WriteLine("UserId: {0} reconnected", userId);
            return base.OnReconnected();
        }

        #endregion

        #region Methods

        public void Send(ChatLine message)
        {
            Console.WriteLine("Received messages: {0}", message.Message);

            if (ConnectionUserId.ContainsKey(message.RecipientId))
            {
                Clients.Client(ConnectionUserId[message.RecipientId]).addMessage(message.Message);
                Console.WriteLine("Message sent to UserId: {0}", message.RecipientId);
            }
        }

        public async Task<IEnumerable<ChatLine>> GetUnreadMessages()
        {
            var userId = GetUserIdFromHeaders();
            var result = await Task.Run<IEnumerable<ChatLine>>(() =>  ChatService.GetUnreadChatLines(userId));
            return result;
        }

        public async Task<ChatLine> GetOneUnreadMessage(int us)
        {
            var userId = GetUserIdFromHeaders();
            var result = await Task.Run<IEnumerable<ChatLine>>(() => ChatService.GetUnreadChatLines(userId));
            return result.First(c => c.RecipientId == userId);
        }

        public string getstuff()
        {
            return "this is a thing";
        }

        #endregion

        #region Privates

       

        private int GetUserIdFromHeaders()
        {
            if (Context.Request.Headers["UserId"] != null)
                return Int32.Parse(Context.Request.Headers["UserId"]);
            return 0;
        }

        #endregion
    }
}