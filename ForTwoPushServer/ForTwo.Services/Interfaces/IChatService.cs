using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;

namespace ForTwo.Services.Interfaces
{
    public interface IChatService
    {
        void SaveChatLine(ChatLine chatLine);
        List<ChatLine> GetUnreadChatLines(int userId);
        List<ChatLine> GetChatLinesPaged(int userId, int pageSize, int chatLineCursorId);

    }
}
