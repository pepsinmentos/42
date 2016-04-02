using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Interop
{
    public class Chat
    {
        public int SenderId { get; set; }
        public int RecipientId { get; set; }
        public string Message { get; set; }
    }
}
