using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using ForTwo.Interop;
using ForTwo.Services.Interfaces;

namespace ForTwo.Api
{
    public class ChatController : ApiController
    {
        protected IChatService ChatService;
        public ChatController(IChatService chatService)
        {
            ChatService = chatService;
        }

        [HttpGet]
        public List<ChatLine> GetUnreadMessages(int userId)
        {
            return ChatService.GetUnreadChatLines(userId);
        }
    }
}
