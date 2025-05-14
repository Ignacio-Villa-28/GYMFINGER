using DPUruNet;
using Microsoft.VisualBasic;
using Npgsql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Runtime.Intrinsics.X86;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using UareUSampleCSharp;
using Constants = DPUruNet.Constants;

namespace BiometricApp
{
    public partial class frmDBEnrollment : Form
    {
        public frmDBEnrollment()
        {
            InitializeComponent();
        }
        /// <summary>
        /// Holds fmds enrolled by the enrollment GUI.
        /// </summary>
        public Dictionary<int, Fmd> Fmds
        {
            get { return fmds; }
            set { fmds = value; }
        }
        private Dictionary<int, Fmd> fmds = new Dictionary<int, Fmd>();

        /// <summary>
        /// Reset the UI causing the user to reselect a reader.
        /// </summary>
        public bool Reset
        {
            get { return reset; }
            set { reset = value; }
        }
        private bool reset;


        private enum Action
        {
            UpdateReaderState,
            SendBitmap,
            SendMessage
        }
        private delegate void SendMessageCallback(Action state, object payload);
        private void SendMessage(Action action, object payload)
        {
            try
            {
                if (this.pbFingerprint.InvokeRequired)
                {
                    SendMessageCallback d = new SendMessageCallback(SendMessage);
                    this.Invoke(d, new object[] { action, payload });
                }
                else
                {
                    switch (action)
                    {
                        case Action.SendMessage:
                            MessageBox.Show((string)payload);
                            break;
                        case Action.SendBitmap:
                            pbFingerprint.Image = (Bitmap)payload;
                            pbFingerprint.Refresh();
                            break;
                    }
                }
            }
            catch (Exception)
            {
            }
        }

        private Reader _reader;

        private ReaderSelection _readerSelection;
        /// <summary>
        /// Hookup capture handler and start capture.
        /// </summary>
        /// <param name="OnCaptured">Delegate to hookup as handler of the On_Captured event</param>
        /// <returns>Returns true if successful; false if unsuccessful</returns>
        public bool StartCaptureAsync(Reader.CaptureCallback OnCaptured)
        {
            using (Tracer tracer = new Tracer("Form_Main::StartCaptureAsync"))
            {
                // Activate capture handler
                currentReader.On_Captured += new Reader.CaptureCallback(OnCaptured);

                // Call capture
                if (!CaptureFingerAsync())
                {
                    return false;
                }

                return true;
            }
        }
        /// <summary>
        /// Check the device status before starting capture.
        /// </summary>
        /// <returns></returns>
        public void GetStatus()
        {
            using (Tracer tracer = new Tracer("Form_Main::GetStatus"))
            {
                Constants.ResultCode result = currentReader.GetStatus();

                if ((result != Constants.ResultCode.DP_SUCCESS))
                {
                    reset = true;
                    throw new Exception("" + result);
                }

                if ((currentReader.Status.Status == Constants.ReaderStatuses.DP_STATUS_BUSY))
                {
                    Thread.Sleep(50);
                }
                else if ((currentReader.Status.Status == Constants.ReaderStatuses.DP_STATUS_NEED_CALIBRATION))
                {
                    currentReader.Calibrate();
                }
                else if ((currentReader.Status.Status != Constants.ReaderStatuses.DP_STATUS_READY))
                {
                    throw new Exception("Reader Status - " + currentReader.Status.Status);
                }
            }
        }
        /// <summary>
        /// Function to capture a finger. Always get status first and calibrate or wait if necessary.  Always check status and capture errors.
        /// </summary>
        /// <param name="fid"></param>
        /// <returns></returns>
        public bool CaptureFingerAsync()
        {
            using (Tracer tracer = new Tracer("Form_Main::CaptureFingerAsync"))
            {
                try
                {
                    GetStatus();

                    Constants.ResultCode captureResult = currentReader.CaptureAsync(Constants.Formats.Fid.ANSI, Constants.CaptureProcessing.DP_IMG_PROC_DEFAULT, currentReader.Capabilities.Resolutions[0]);
                    if (captureResult != Constants.ResultCode.DP_SUCCESS)
                    {
                        reset = true;
                        throw new Exception("" + captureResult);
                    }

                    return true;
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Error:  " + ex.Message);
                    return false;
                }
            }
        }
        /// <summary>
        /// Cancel the capture and then close the reader.
        /// </summary>
        /// <param name="OnCaptured">Delegate to unhook as handler of the On_Captured event </param>
        public void CancelCaptureAndCloseReader(Reader.CaptureCallback OnCaptured)
        {
            using (Tracer tracer = new Tracer("Form_Main::CancelCaptureAndCloseReader"))
            {
                if (currentReader != null)
                {
                    currentReader.CancelCapture();

                    // Dispose of reader handle and unhook reader events.
                    currentReader.Dispose();

                    if (reset)
                    {
                        CurrentReader = null;
                    }
                }
            }
        }
        // When set by child forms, shows s/n and enables buttons.
        private Reader currentReader;
        public Reader CurrentReader
        {
            get { return currentReader; }
            set
            {
                currentReader = value;
                SendMessage(Action.UpdateReaderState, value);
            }
        }
        private ReaderCollection _readers;
        private void LoadScanners()
        {
            cboReaders.Text = string.Empty;
            cboReaders.Items.Clear();
            cboReaders.SelectedIndex = -1;

            try
            {
                _readers = ReaderCollection.GetReaders();

                foreach (Reader Reader in _readers)
                {
                    cboReaders.Items.Add(Reader.Description.Name);
                }

                if (cboReaders.Items.Count > 0)
                {
                    cboReaders.SelectedIndex = 0;
                    //btnCaps.Enabled = true;
                    //btnSelect.Enabled = true;
                }
                else
                {
                    //btnSelect.Enabled = false;
                    //btnCaps.Enabled = false;
                }
            }
            catch (Exception ex)
            {
                //message box:
                String text = ex.Message;
                text += "\r\n\r\nPlease check if DigitalPersona service has been started";
                String caption = "Cannot access readers";
                MessageBox.Show(text, caption);
            }
        }
        private void frmDBEnrollment_Load(object sender, EventArgs e)
        {
            // Reset variables
            LoadScanners();
            firstFinger = null;
            resultEnrollment = null;
            preenrollmentFmds = new List<Fmd>();
            pbFingerprint.Image = null;
            if (CurrentReader != null)
            {
                CurrentReader.Dispose();
                CurrentReader = null;
            }
            CurrentReader = _readers[cboReaders.SelectedIndex];
            if (!OpenReader())
            {
                //this.Close();
            }

            if (!StartCaptureAsync(this.OnCaptured))
            {
                //this.Close();
            }

            using (var conn = new NpgsqlConnection("Host=localhost;Username=postgres;Password=123456789;Database=Registro_Gimnasio;"))
            {
                conn.Open();

                // Limpiar ComboBox antes de llenarlo
                cmbLedger.Items.Clear();

                // Consultar matrícula de estudiantes
                using (var cmd = new NpgsqlCommand("SELECT matricula FROM estudiante", conn))
                {
                    using (var reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            cmbLedger.Items.Add(reader["matricula"].ToString());
                        }
                    }
                }

                // Consultar folio de externos
                using (var cmd = new NpgsqlCommand("SELECT folio FROM externos", conn))
                {
                    using (var reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            cmbLedger.Items.Add(reader["folio"].ToString());
                        }
                    }
                }

                // Consultar número de empleado de docentes/administrativos
                using (var cmd = new NpgsqlCommand("SELECT num_empleado FROM docente_administrativo", conn))
                {
                    using (var reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            cmbLedger.Items.Add(reader["num_empleado"].ToString());
                        }
                    }
                }
            }

            // Si hay datos, seleccionar el primer valor por defecto
            if (cmbLedger.Items.Count > 0)
                cmbLedger.SelectedIndex = 0;

            MessageBox.Show("Datos cargados correctamente en el ComboBox.");


        }
        /// <summary>
        /// Open a device and check result for errors.
        /// </summary>
        /// <returns>Returns true if successful; false if unsuccessful</returns>
        public bool OpenReader()
        {
            using (Tracer tracer = new Tracer("Form_Main::OpenReader"))
            {
                reset = false;
                Constants.ResultCode result = Constants.ResultCode.DP_DEVICE_FAILURE;

                // Open reader
                result = currentReader.Open(Constants.CapturePriority.DP_PRIORITY_COOPERATIVE);

                if (result != Constants.ResultCode.DP_SUCCESS)
                {
                    MessageBox.Show("Error:  " + result);
                    reset = true;
                    return false;
                }

                return true;
            }
        }
        /// <summary>
        /// Check quality of the resulting capture.
        /// </summary>
        public bool CheckCaptureResult(CaptureResult captureResult)
        {
            using (Tracer tracer = new Tracer("Form_Main::CheckCaptureResult"))
            {
                if (captureResult.Data == null || captureResult.ResultCode != Constants.ResultCode.DP_SUCCESS)
                {
                    if (captureResult.ResultCode != Constants.ResultCode.DP_SUCCESS)
                    {
                        reset = true;
                        throw new Exception(captureResult.ResultCode.ToString());
                    }

                    // Send message if quality shows fake finger
                    if ((captureResult.Quality != Constants.CaptureQuality.DP_QUALITY_CANCELED))
                    {
                        throw new Exception("Quality - " + captureResult.Quality);
                    }
                    return false;
                }

                return true;
            }
        }
        private const int PROBABILITY_ONE = 0x7fffffff;
        private Fmd firstFinger;
        int count = 0;
        DataResult<Fmd> resultEnrollment;
        List<Fmd> preenrollmentFmds;
        /// <summary>
        /// Handler for when a fingerprint is captured.
        /// </summary>
        /// <param name="captureResult">contains info and data on the fingerprint capture</param>
        public void OnCaptured(CaptureResult captureResult)
        {
            try
            {
                // Check capture quality and throw an error if bad.
                if (!CheckCaptureResult(captureResult)) return;

                // Create bitmap
                foreach (Fid.Fiv fiv in captureResult.Data.Views)
                {
                    SendMessage(Action.SendBitmap, CreateBitmap(fiv.RawImage, fiv.Width, fiv.Height));
                }

                //Enrollment Code:
                try
                {
                    count++;
                    // Check capture quality and throw an error if bad.
                    DataResult<Fmd> resultConversion = FeatureExtraction.CreateFmdFromFid(captureResult.Data, Constants.Formats.Fmd.ANSI);

                    MessageBox.Show("A finger was captured.  \r\nCount:  " + (count));

                    if (resultConversion.ResultCode != Constants.ResultCode.DP_SUCCESS)
                    {
                        Reset = true;
                        throw new Exception(resultConversion.ResultCode.ToString());
                    }

                    preenrollmentFmds.Add(resultConversion.Data);

                    if (count >= 4)
                    {
                        resultEnrollment = DPUruNet.Enrollment.CreateEnrollmentFmd(Constants.Formats.Fmd.ANSI, preenrollmentFmds);

                        if (resultEnrollment.ResultCode == Constants.ResultCode.DP_SUCCESS)
                        {
                            preenrollmentFmds.Clear();
                            count = 0;
                            //obj_bal_ForAll.BAL_StoreCustomerFPData("tbl_Finger", txtledgerId.Text, Fmd.SerializeXml(resultEnrollment.Data));
                            MessageBox.Show("Customer Finger Print was successfully enrolled.");
                            return;
                        }
                        else if (resultEnrollment.ResultCode == Constants.ResultCode.DP_ENROLLMENT_INVALID_SET)
                        {
                            SendMessage(Action.SendMessage, "Enrollment was unsuccessful.  Please try again.");
                            preenrollmentFmds.Clear();
                            count = 0;
                            return;
                        }
                    }
                    MessageBox.Show("Now place the same finger on the reader.");
                }
                catch (Exception ex)
                {
                    // Send error message, then close form
                    SendMessage(Action.SendMessage, "Error:  " + ex.Message);
                }
            }
            catch (Exception ex)
            {
                // Send error message, then close form
                SendMessage(Action.SendMessage, "Error:  " + ex.Message);
            }
        }
        /// <summary>
        /// Create a bitmap from raw data in row/column format.
        /// </summary>
        /// <param name="bytes"></param>
        /// <param name="width"></param>
        /// <param name="height"></param>
        /// <returns></returns>
        public Bitmap CreateBitmap(byte[] bytes, int width, int height)
        {
            byte[] rgbBytes = new byte[bytes.Length * 3];

            for (int i = 0; i <= bytes.Length - 1; i++)
            {
                rgbBytes[(i * 3)] = bytes[i];
                rgbBytes[(i * 3) + 1] = bytes[i];
                rgbBytes[(i * 3) + 2] = bytes[i];
            }
            Bitmap bmp = new Bitmap(width, height, PixelFormat.Format24bppRgb);

            BitmapData data = bmp.LockBits(new Rectangle(0, 0, bmp.Width, bmp.Height), ImageLockMode.WriteOnly, PixelFormat.Format24bppRgb);

            for (int i = 0; i <= bmp.Height - 1; i++)
            {
                IntPtr p = new IntPtr(data.Scan0.ToInt64() + data.Stride * i);
                System.Runtime.InteropServices.Marshal.Copy(rgbBytes, i * bmp.Width * 3, p, bmp.Width * 3);
            }

            bmp.UnlockBits(data);

            return bmp;
        }

        public NpgsqlConnection conn = new NpgsqlConnection("Host=localhost;Username=postgres;Password=123456789;Database=Registro_Gimnasio;");

        private void frmDBEnrollment_FormClosing(object sender, FormClosingEventArgs e)
        {
            CancelCaptureAndCloseReader(this.OnCaptured);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (resultEnrollment == null || resultEnrollment.Data == null)
            {
                MessageBox.Show("No se ha capturado una huella válida.");
                return;
            }

            if (cmbLedger.SelectedItem == null)
            {
                MessageBox.Show("Debe seleccionar un identificador antes de registrar la huella.");
                return;
            }

            string selectedValue = cmbLedger.SelectedItem.ToString();
            string tipoUsuario = ""; // Variable para determinar el tipo de usuario

            // Determinar el tipo de usuario basándose en el identificador seleccionado
            using (var conn = new NpgsqlConnection("Host=localhost;Username=postgres;Password=123456789;Database=Registro_Gimnasio;"))
            {
                conn.Open();

                // Consultar en la tabla estudiantes
                using (var cmd = new NpgsqlCommand("SELECT carrera FROM estudiante WHERE matricula = @id", conn))
                {
                    cmd.Parameters.AddWithValue("@id", selectedValue);
                    var result = cmd.ExecuteScalar();
                    if (result != null)
                        tipoUsuario = "carrera";
                }

                // Consultar en la tabla externos si no es estudiante
                if (string.IsNullOrEmpty(tipoUsuario))
                {
                    using (var cmd = new NpgsqlCommand("SELECT procedencia FROM externos WHERE folio = @id", conn))
                    {
                        cmd.Parameters.AddWithValue("@id", selectedValue);
                        var result = cmd.ExecuteScalar();
                        if (result != null)
                            tipoUsuario = "procedencia";
                    }
                }

                // Consultar en la tabla docente_administrativo si no es estudiante ni externo
                if (string.IsNullOrEmpty(tipoUsuario))
                {
                    using (var cmd = new NpgsqlCommand("SELECT tipo_empleado FROM docente_administrativo WHERE num_empleado = @id", conn))
                    {
                        cmd.Parameters.AddWithValue("@id", selectedValue);
                        var result = cmd.ExecuteScalar();
                        if (result != null)
                            tipoUsuario = "tipo_empleado";
                    }
                }

                if (string.IsNullOrEmpty(tipoUsuario))
                {
                    MessageBox.Show("No se encontró el tipo de usuario para el identificador seleccionado.");
                    return;
                }

                // Insertar en la tabla bitacora con los valores correctos
                using (var cmd = new NpgsqlCommand("INSERT INTO bitacora (matricula, folio, num_empleado, horayfecha, tipo_usuario, huella) VALUES (@matricula, @folio, @num_empleado, NOW(), @tipoUsuario, @huella)", conn))
                {
                    cmd.Parameters.AddWithValue("@matricula", tipoUsuario == "carrera" ? (object)selectedValue : DBNull.Value);
                    cmd.Parameters.AddWithValue("@folio", tipoUsuario == "procedencia" ? (object)selectedValue : DBNull.Value);
                    cmd.Parameters.AddWithValue("@num_empleado", tipoUsuario == "tipo_empleado" ? (object)selectedValue : DBNull.Value);
                    cmd.Parameters.AddWithValue("@tipoUsuario", tipoUsuario);
                    cmd.Parameters.AddWithValue("@huella", Fmd.SerializeXml(resultEnrollment.Data));

                    cmd.ExecuteNonQuery();
                    MessageBox.Show("Huella guardada correctamente en la tabla bitacora.");
                }
            }
        }

        private void btnBack_Click(object sender, EventArgs e)
        {
            try
            {
                // Verifica si la conexión está abierta antes de cerrarla
                if (conn != null && conn.State == ConnectionState.Open)
                {
                    conn.Close();
                    conn.Dispose(); // Libera los recursos de la conexión
                }

                // Cierra el formulario actual
                this.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al cerrar la conexión: " + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }

        private void btnVerify_Click(object sender, EventArgs e)
        {
            frmDBVerify verifyForm = new frmDBVerify();

            verifyForm.Show();

            this.Hide();
        }
    }
}
