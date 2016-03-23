using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;
using ForTwo.Services.Interfaces;
using ForTwo.Services.Repositories.SQL;

namespace ForTwo.Services.Repositories
{
    public class ChatPersistRepository : BaseRepositorySql, IChatPersist
    {
        protected IExceptionService ExceptionService;

        public ChatPersistRepository(IExceptionService exceptionService, IDatabaseConfig config)
            : base(config)
        {
            ExceptionService = exceptionService;
        }

        public ChatLine SaveChatLine(ChatLine chatLine)
        {
            try
            {
                return GetOne<ChatLine>("usp_InsertChatLine", new 
                { 
                    @ChatLineContent = chatLine.Message,
                    chatLine.RecipientId,
                    chatLine.SenderId,
                    @ChatLineStatus = chatLine.Status,
                    chatLine.SentOn
                });
            }
            catch (Exception ex)
            {
                ExceptionService.HandleException(ex);
                return new ChatLine();
            }
        }

        
    }
}
