using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;

namespace ForTwo.Services.Interfaces
{
    public interface IChatReader
    {
        List<ChatLine> GetUnreadChatLines(int userId);
    }
}
