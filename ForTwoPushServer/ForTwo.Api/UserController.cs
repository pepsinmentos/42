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
    public class UserController : ApiController
    {
        protected IUserService UserService;

        public UserController()
        {
            
        }

        public UserController(IUserService userService)
        {
            UserService = userService;
        }

        [HttpGet]
        public List<User> Get()
        {
            return UserService.All();
        }

        [HttpGet]
        public User Get(string id)
        {
            int userId = 0;
            if (Int32.TryParse(id, out userId))
            {
                return UserService.GetById(userId);
            }
            else
            {
                return UserService.GetByEmail(id);
            }
        }
    }
}
