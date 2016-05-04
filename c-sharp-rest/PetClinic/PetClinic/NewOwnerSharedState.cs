using System;

namespace PetClinic
{
	public class NewOwnerSharedState
	{
		public void e_CorrectData() {
			Console.WriteLine ("NewOwnerSharedState.e_CorrectData");
		}

		public void e_IncorrectData() {
			Console.WriteLine ("NewOwnerSharedState.e_IncorrectData");
		}

		public void v_IncorrectData() {
			Console.WriteLine ("NewOwnerSharedState.v_IncorrectData");
		}

		public void v_NewOwner() {
			Console.WriteLine ("NewOwnerSharedState.v_NewOwner");
		}

		public void v_OwnerInformation() {
			Console.WriteLine ("NewOwnerSharedState.v_OwnerInformation");
		}
	}
}

