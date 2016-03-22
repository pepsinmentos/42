using ForTwo.Interop;

namespace ForTwo.Services.Interfaces
{
    public interface IChatPersist
    {
        ChatLine SaveChatLine(ChatLine chatLine); 
    }
}
