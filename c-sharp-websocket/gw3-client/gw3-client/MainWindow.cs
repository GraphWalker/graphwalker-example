using System;
using Gtk;
using Alchemy;
using Alchemy.Classes;

public partial class MainWindow: Gtk.Window
{
	WebSocketClient ws = null;

	public MainWindow () : base (Gtk.WindowType.Toplevel)
	{
		Build ();
		host.Text = "ws://localhost:8887/";
	}

	protected void OnDeleteEvent (object sender, DeleteEventArgs a)
	{
		ws.Disconnect ();
		Application.Quit ();
		a.RetVal = true;
	}

	protected void onConnectToggled (object sender, EventArgs e)
	{
		ToggleButton connectBtn = sender as ToggleButton;
		if (connectBtn.Active) {
			if (createSocket ()) {
				enableWidgets (true);
				ws.Connect ();
				connectBtn.Label = "Connected";
			} else {
				connectBtn.Active = false;
			}
		} else {
			enableWidgets (false);
			if (ws != null) {
				ws.Disconnect ();
			}
			connectBtn.Label = "Connect";
		}
	}

	protected void enableWidgets (bool enable)
	{
		modelFilePicker.Sensitive = enable;
		getNext.Sensitive = enable;
		hasNext.Sensitive = enable;
		start.Sensitive = enable;
		restart.Sensitive = enable;
		getData.Sensitive = enable;
	}

	protected void logText (string text)
	{
		Gtk.Application.Invoke (delegate { 
			TextIter mIter = log.Buffer.EndIter; 
			log.Buffer.Insert (ref mIter, text);
			log.Buffer.Insert (ref mIter, System.Environment.NewLine);
			log.ScrollToIter (log.Buffer.EndIter, 0, false, 0, 0); 
		}); 
	}

	protected bool createSocket ()
	{
		if (string.IsNullOrEmpty (host.Text)) {
			logText ("No host name");
			return false;
		}
		try {
			ws = new WebSocketClient (host.Text) {
				OnReceive = OnReceive,
				OnDisconnect = OnDisconnect
			};
			return true;
		} catch (System.Exception e) {
			logText ("Not a valid host name: " + e.Message);
			return false;
		}
	}

	void OnReceive (UserContext context)
	{
		logText (context.DataFrame.ToString ());
	}

	void OnDisconnect (UserContext context)
	{
		logText (context.DataFrame.ToString ());
	}

	protected void onGetNext (object sender, EventArgs e)
	{
		ws.Send ("{\"type\":\"getNext\"}");
	}

	protected void OnHasNext (object sender, EventArgs e)
	{
		ws.Send ("{\"type\":\"hasNext\"}");
	}

	protected void OnStart (object sender, EventArgs e)
	{
		ws.Send ("{\"type\":\"start\"}");
	}

	protected void OnRestart (object sender, EventArgs e)
	{
		ws.Send ("{\"type\":\"restart\"}");
	}

	protected void OnGetData (object sender, EventArgs e)
	{
		ws.Send ("{\"type\":\"getData\"}");
	}

	protected void OnModelSelectionChanged (object sender, EventArgs e)
	{
		ws.Send (System.IO.File.ReadAllText (modelFilePicker.Filename));
	}
}
