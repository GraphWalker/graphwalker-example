using System;
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox;
using FluentAssertions.Execution;
using System.Threading;
using System.Collections.Generic;
using System.Linq;


namespace PetClinic
{
    class Helper
    {
        private static IWebDriver driver;

        /**
        * Timeout time in seconds used for waiting for element(s) to show up.
        */
        static int timeOut = 10;

        public static IWebDriver GetWebDriver()
        {
            if (driver == null)
            {
                driver = new FirefoxDriver();
            }
            return driver;
        }

        /**
         * Will wait for a specified web element to appear. If not found
         * an assertion will fail.
         *
         * @param by The description of the element
         * @return The matching element if found.
         */
        public static IWebElement WaitForElement(By by)
        {
            for (int second = 0; ; second++)
            {
                if (second >= timeOut)
                {
                    Execute.Assertion.FailWith("Timeout occurred while waiting for: " + by.ToString());
                }
                try
                {
                    return GetWebDriver().FindElement(by);
                }
                catch (Exception)
                {
                    Thread.Sleep(1000);
                }
            }
        }

        /**
         * Will wait for a specified web element(s) to appear. If not found
         * an assertion will fail.
         *
         * @param by The description of the element
         * @return A list of matching element(s) if found.
         */
        public static List<IWebElement> WaitForElements(By by)
        {
            for (int second = 0; ; second++)
            {
                if (second >= timeOut)
                {
                    Execute.Assertion.FailWith("Timeout occurred while waiting for: " + by.ToString());
                }
                
                try
                {
                    return new List<IWebElement>(GetWebDriver().FindElements(by));
                }
                catch (Exception e)
                {
                    Thread.Sleep(1000);
                }
            }
        }

        public static string RandomAlphaString(int length)
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdfeghijklmnopqrstuvxyz";
            var random = new Random();
            return new string(Enumerable.Repeat(chars, length)
              .Select(s => s[random.Next(s.Length)]).ToArray());
        }

        public static string RandomNumberString(int length)
        {
            const string chars = "0123456789";
            var random = new Random();
            return new string(Enumerable.Repeat(chars, length)
              .Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }
}
