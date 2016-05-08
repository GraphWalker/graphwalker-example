using FluentAssertions;
using OpenQA.Selenium;
using System;

namespace PetClinic
{
	public class FindOwnersSharedState
	{
		public void e_AddOwner() {
            Helper.WaitForElement(By.LinkText("Add Owner")).Click();
        }

        public void e_FindOwners() {
            Helper.WaitForElement(By.ClassName("icon-search")).Click();
        }

        public void e_Search() {
            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void v_FindOwners() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Find Owners");
        }

        public void v_NewOwner() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("New Owner");
        }

        public void v_Owners() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Owners");
        }
	}
}

