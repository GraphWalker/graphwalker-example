using FluentAssertions;
using Newtonsoft.Json.Linq;
using OpenQA.Selenium;
using OpenQA.Selenium.Support.UI;
using System;

namespace PetClinic
{
	public class OwnerInformationSharedState
	{
		public void e_AddNewPet() {
            Helper.WaitForElement(By.LinkText("Add New Pet")).Click();
        }

        public void e_AddPetFailed() {
            Helper.WaitForElement(By.Id("name")).Clear();
            Helper.WaitForElement(By.Id("birthDate")).Clear();
            Helper.WaitForElement(By.Id("birthDate")).SendKeys("2015/02/05");
            new SelectElement( Helper.WaitForElement(By.Id("type"))).SelectByText("dog");
            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void e_AddPetSuccessfully() {
            Helper.WaitForElement(By.Id("birthDate")).Clear();
            Helper.WaitForElement(By.Id("birthDate")).SendKeys("2015/02/05");
            Helper.WaitForElement(By.Id("name")).Clear();
            Helper.WaitForElement(By.Id("name")).SendKeys(Helper.RandomAlphaString(10));
            new SelectElement(Helper.WaitForElement(By.Id("type"))).SelectByText("dog");
            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void e_AddVisit() {
            Helper.WaitForElement(By.LinkText("Add Visit")).Click();
        }

        public void e_EditPet() {
            Helper.WaitForElement(By.LinkText("Edit Pet")).Click();
        }

        public void e_FindOwners() {
            Helper.WaitForElement(By.ClassName("icon-search")).Click();
        }

        public void e_UpdatePet() {
            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void e_VisitAddedFailed() {
            Helper.WaitForElement(By.Id("description")).Clear();
            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void e_VisitAddedSuccessfully() {
            Helper.WaitForElement(By.Id("description")).Clear();
            Helper.WaitForElement(By.Id("description")).SendKeys((Helper.RandomAlphaString(10)));
            Helper.WaitForElement(By.CssSelector("button[type=\"submit\"]")).Click();
        }

        public void v_FindOwners() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Find Owners");
		}

		public void v_NewPet() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("New Pet");
        }

        public void v_NewVisit() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("New Visit");
        }

        public void v_OwnerInformation() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Owner Information");
            GraphWalkerRestClient.setData("numOfPets = " + Helper.WaitForElements(By.XPath("//th[2]")).Count + ";");

            JObject jsonData = GraphWalkerRestClient.getData();
            Console.WriteLine("  Number of pets (the data is fetched from PetClinic and set in the model): " + jsonData.GetValue("numOfPets"));
        }

        public void v_Pet() {
            Helper.WaitForElement(By.TagName("h2")).Text.Should().Match("Pet");
        }
    }
}

