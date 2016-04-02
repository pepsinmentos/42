using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ForTwo.Interop;
using ForTwo.Services.Interfaces;

namespace ForTwo.Services
{
    public class UserService : IUserService
    {
        public List<User> All()
        {
            return new List<User>() {
                new User() { Email = "pieter.roodt@gmail.com", Name = "Pieter Roodt", UserId = 1},
                new User() { Email = "ldaoyi@gmail.com", Name = "Daoyi Liu", UserId = 2},
            };
        }

        public User GetByEmail(string email)
        {
            if (All().Any(user => user.Email == email))
                return All().First(user => user.Email == email);

            return new User();
        }

        public User GetById(int id)
        {
            if (All().Any(user => user.UserId == id))
                return All().First(user => user.UserId == id);

            return new User();
        }
    }
}
