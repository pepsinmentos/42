using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;
using Microsoft.AspNet.SignalR;

namespace ForTwoPushServer
{
    public class ChatHub : Hub
    {
        private Dictionary<int, string> ConnectionUserId;

        public ChatHub(Dictionary<int, string> connectionUserId)
        {
            ConnectionUserId = connectionUserId;
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
            return base.OnConnected();
        }

        public override Task OnDisconnected(bool stopCalled)
        {
            if (ConnectionUserId.ContainsKey(GetUserIdFromHeaders()))
            {
                ConnectionUserId.Remove(GetUserIdFromHeaders());
            }
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
            return base.OnReconnected();
        }

        #endregion

        #region Methods

        public void Send(ChatLine message)
        {
            if (ConnectionUserId.ContainsKey(message.RecipientId))
                Clients.Client(ConnectionUserId[message.RecipientId]).addMessage(message);
        }

        public async Task<IEnumerable<ChatLine>> GetUnreadMessages()
        {
            var userId = GetUserIdFromHeaders();
            var result = await Task.Run<IEnumerable<ChatLine>>(() => UnreadMessages());
            return result.Where(c => c.RecipientId == userId);
        }

        #endregion

        #region Privates

        private List<ChatLine> UnreadMessages()
        {
            return new List<ChatLine>()
            {
                new ChatLine() {Message = "This is a delayed message", SenderId = 1, RecipientId = 2},
                new ChatLine() {Message = "Dinner is ready", SenderId = 1, RecipientId = 2},
                new ChatLine() {Message = "Dude... I just saw a snake", SenderId = 2, RecipientId = 1},
                new ChatLine() {Message = "Can you believe this guy? ", SenderId = 4, RecipientId = 3},
                new ChatLine()
                {
                    Message = "How many times have you listened to music today?",
                    SenderId = 4,
                    RecipientId = 3
                }
            };
        }

        private int GetUserIdFromHeaders()
        {
            return Int32.Parse(Context.Request.Headers["UserId"]);
        }

        #endregion
    }
}