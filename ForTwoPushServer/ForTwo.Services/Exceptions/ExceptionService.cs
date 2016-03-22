using System;
using System.Collections.Generic;
using System.Linq;
using ForTwo.Services.Interfaces;

namespace ForTwo.Services.Exceptions
{
    public class ExceptionService : IExceptionService
    {
        protected List<ExceptionHandler> Handlers;

        public ExceptionService(IEnumerable<ExceptionHandler> handlers)
        {
            if (handlers != null)
                Handlers = handlers.ToList();
        }

        public void HandleException(Exception ex)
        {
            if (Handlers != null && Handlers.Any())
            {
                foreach (var exceptionHandler in Handlers)
                {
                    try
                    {
                        exceptionHandler.Handle(ex);
                    }
                    catch
                    {
                    }
                    
                }
                
            }
        }
    }
}