using FluentAssertions;
using OpenQA.Selenium;
using System;

namespace PetClinic
{
	public class PetClinicSharedState
	{
		public void v_FindOwners() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Find Owners");
        }

		public void e_HomePage() {
            Helper.WaitForElement(By.ClassName("icon-home")).Click();
        }

		public void e_Veterinarians() {
            Helper.WaitForElement(By.ClassName("icon-th-list")).Click();
        }

		public void v_Veterinarians() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Veterinarians");
        }

		public void e_FindOwners() {
            Helper.WaitForElement(By.ClassName("icon-search")).Click();
        }

		public void v_HomePage() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Welcome");
        } 

		public void e_StartBrowser() {
            Helper.GetWebDriver().Navigate().GoToUrl("http://localhost:9966/petclinic/");
        }
	}
}

