using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ForTwo.Interop
{
    public class ChatLine
    {
        public long ChatLineId { get; set; }
        public int SenderId { get; set; }
        public int RecipientId { get; set; }
        public string Message { get; set; }
        public int Status { get; set; }
        public DateTime SentOn { get; set; }
        public DateTime CreatedOn { get; set; }
    }
}
