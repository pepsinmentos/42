using System;

namespace ForTwo.Services.Exceptions
{
    public abstract class ExceptionHandler
    {
        public abstract void Handle(Exception ex);
    }
}
