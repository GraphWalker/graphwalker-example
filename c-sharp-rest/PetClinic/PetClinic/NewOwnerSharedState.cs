using FluentAssertions;
using OpenQA.Selenium;
using System;

namespace PetClinic
{
	public class NewOwnerSharedState
	{
		public void e_CorrectData() {
            Helper.WaitForElement(By.Id("firstName")).Clear();
            Helper.WaitForElement(By.Id("firstName")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("lastName")).Clear();
            Helper.WaitForElement(By.Id("lastName")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("address")).Clear();
            Helper.WaitForElement(By.Id("address")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("city")).Clear();
            Helper.WaitForElement(By.Id("city")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("telephone")).Clear();
            Helper.WaitForElement(By.Id("telephone")).SendKeys(Helper.RandomNumberString(10));

            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

		public void e_IncorrectData() {
            Helper.WaitForElement(By.Id("firstName")).Clear();
            Helper.WaitForElement(By.Id("firstName")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("lastName")).Clear();
            Helper.WaitForElement(By.Id("lastName")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("address")).Clear();
            Helper.WaitForElement(By.Id("address")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("city")).Clear();
            Helper.WaitForElement(By.Id("city")).SendKeys(Helper.RandomAlphaString(10));

            Helper.WaitForElement(By.Id("telephone")).Clear();
            Helper.WaitForElement(By.Id("telephone")).SendKeys(Helper.RandomNumberString(20));

            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void v_IncorrectData() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("New Owner");

            String str = Helper.WaitForElement(By.CssSelector("div.control-group.error > div.controls > span.help-inline")).Text;
            str.Should().Contain("numeric value out of bounds (<10 digits>.<0 digits> expected");
        }

        public void v_NewOwner() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("New Owner");
        }

        public void v_OwnerInformation() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Owner Information");
        }
    }
}

