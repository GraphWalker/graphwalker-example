using FluentAssertions;
using OpenQA.Selenium;
using System;

namespace PetClinic
{
	public class VeterinariensSharedState
	{
		public void e_Search() {
            Helper.WaitForElement(By.CssSelector("input[type=\"search\"]")).Clear();
            Helper.WaitForElement(By.CssSelector("input[type=\"search\"]")).SendKeys("helen");
        }

        public void v_SearchResult() {
            String bodyText = Helper.WaitForElement(By.XPath("//table[@id='vets']/tbody/tr/td")).Text;
            bodyText.Should().Contain("Helen Leary");
        }

        public void v_Veterinarians() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Veterinarians");
        }
    }
}

