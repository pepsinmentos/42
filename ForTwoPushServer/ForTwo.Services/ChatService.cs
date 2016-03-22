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

        public ChatService(IChatPersist chatPersist)
        {
            ChatPersist = chatPersist;
        }

        public void SaveChatLine(ChatLine chatLine)
        {
            try
            {
                ChatPersist.SaveChatLine(chatLine);
            }
            catch (Exception ex)
            {
                
                
            }
            
        }

        public List<ChatLine> GetUnreadChatLines(int userId)
        {
            throw new NotImplementedException();
        }

        public List<ChatLine> GetChatLinesPaged(int userId, int pageSize, int chatLineCursorId)
        {
            throw new NotImplementedException();
        }
    }
}
