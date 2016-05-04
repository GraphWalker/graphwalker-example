using System;

namespace PetClinic
{
	public class FindOwnersSharedState
	{
		public void e_AddOwner() {
			Console.WriteLine ("FindOwnersSharedState.e_AddOwner");
		}

		public void e_FindOwners() {
			Console.WriteLine ("FindOwnersSharedState.e_FindOwners");
		}

		public void e_Search() {
			Console.WriteLine ("FindOwnersSharedState.e_Search");
		}

		public void v_FindOwners() {
			Console.WriteLine ("FindOwnersSharedState.v_FindOwners");
		}

		public void v_NewOwner() {
			Console.WriteLine ("FindOwnersSharedState.v_NewOwner");
		}

		public void v_Owners() {
			Console.WriteLine ("FindOwnersSharedState.v_Owners");
		}
	}
}

