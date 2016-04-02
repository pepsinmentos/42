using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using ForTwo.Services.Interfaces;
using ForTwo.Services.Repositories;

namespace ForTwo.Services
{
    public static class IoCRegistry
    {
        public static void Configure(ContainerBuilder builder)
        {
            builder.RegisterType<UserService>().As<IUserService>();
            builder.RegisterType<ChatService>().As<IChatService>();
            builder.RegisterType<ChatPersistRepository>().As<IChatPersist>();
        }
    }
}
