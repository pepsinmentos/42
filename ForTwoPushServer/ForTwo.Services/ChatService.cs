using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;
using ForTwo.Services.Interfaces;

namespace ForTwo.Services
{
    public class ChatService : IChatService
    {
        protected readonly IChatPersist ChatPersist;
        protected readonly IChatReader ChatReader;

        public ChatService()
        {
            //ChatPersist = chatPersist;
        }

        public void SaveChatLine(ChatLine chatLine)
        {
            try
            {
               // ChatPersist.SaveChatLine(chatLine);
            }
            catch (Exception ex)
            {
                
                
            }
            
        }

        public List<ChatLine> GetUnreadChatLines(int userId)
        {
            return UnreadMessages().Where(x => x.RecipientId == userId).ToList();   
        }

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
        public List<ChatLine> GetChatLinesPaged(int userId, int pageSize, int chatLineCursorId)
        {
            throw new NotImplementedException();
        }
    }
}
