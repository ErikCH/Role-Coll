#region Namespace Inclusions
using System;
using System.IO.Ports;
using System.Windows.Forms;
#endregion
namespace SerialPortExample
{
	class SerialPortProgram
	{
		// Create the serial port with basic settings  
		private SerialPort port = new SerialPort("COM4", 9600, Parity.None, 8, StopBits.One);
		[STAThread]
		static void Main(string[] args)
		{
			// Instatiate this class  
			new SerialPortProgram();
		}
		private SerialPortProgram()
		{
			Console.WriteLine("Incoming Data Fom COM4:");
			// Attach a method to be called when there is data waiting in the port's buffer  
			port.DataReceived += new SerialDataReceivedEventHandler(port_DataReceived);
			// Begin communications  
			port.Open();
			// Enter an application loop which keeps this thread alive  
			Application.Run();
		}
		private void port_DataReceived(object sender, SerialDataReceivedEventArgs e)
		{
			// Show all the incoming data in the port's buffer
			string test = port.ReadExisting();
			if (test.Contains("F"))
			{
				Console.WriteLine("Forward");
				try
				{
					SendKeys.SendWait("{PGDN}");
				}
				catch (Exception ex) { Console.WriteLine(ex.ToString()); }
			}
			else if (test.Contains("V"))
			{
				Console.WriteLine("Reverse");
				try
				{
					SendKeys.SendWait("{PGUP}");
				}
				catch (Exception ex) { Console.WriteLine(ex.ToString()); }
				
			}
		}
	}
}