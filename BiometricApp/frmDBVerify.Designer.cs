namespace BiometricApp
{
    partial class frmDBVerify
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            cboReaders = new ComboBox();
            lblSelectReader = new Label();
            lblPlaceFinger = new Label();
            pbFingerprint = new PictureBox();
            button1 = new Button();
            button2 = new Button();
            button3 = new Button();
            ((System.ComponentModel.ISupportInitialize)pbFingerprint).BeginInit();
            SuspendLayout();
            // 
            // cboReaders
            // 
            cboReaders.Font = new Font("Tahoma", 8F);
            cboReaders.Location = new Point(63, 380);
            cboReaders.Margin = new Padding(4, 5, 4, 5);
            cboReaders.Name = "cboReaders";
            cboReaders.Size = new Size(211, 24);
            cboReaders.TabIndex = 21;
            // 
            // lblSelectReader
            // 
            lblSelectReader.Location = new Point(59, 356);
            lblSelectReader.Margin = new Padding(4, 0, 4, 0);
            lblSelectReader.Name = "lblSelectReader";
            lblSelectReader.Size = new Size(161, 20);
            lblSelectReader.TabIndex = 20;
            lblSelectReader.Text = "Select Reader:";
            // 
            // lblPlaceFinger
            // 
            lblPlaceFinger.Location = new Point(60, 308);
            lblPlaceFinger.Margin = new Padding(4, 0, 4, 0);
            lblPlaceFinger.Name = "lblPlaceFinger";
            lblPlaceFinger.Size = new Size(195, 29);
            lblPlaceFinger.TabIndex = 18;
            lblPlaceFinger.Text = "Place a finger on the reader";
            // 
            // pbFingerprint
            // 
            pbFingerprint.Location = new Point(63, 43);
            pbFingerprint.Margin = new Padding(4, 5, 4, 5);
            pbFingerprint.Name = "pbFingerprint";
            pbFingerprint.Size = new Size(212, 260);
            pbFingerprint.SizeMode = PictureBoxSizeMode.Zoom;
            pbFingerprint.TabIndex = 19;
            pbFingerprint.TabStop = false;
            // 
            // button1
            // 
            button1.Location = new Point(59, 429);
            button1.Name = "button1";
            button1.Size = new Size(94, 29);
            button1.TabIndex = 22;
            button1.Text = "Registrar";
            button1.UseVisualStyleBackColor = true;
            button1.Click += btnRegister_Click;
            // 
            // button2
            // 
            button2.Location = new Point(111, 523);
            button2.Name = "button2";
            button2.Size = new Size(94, 29);
            button2.TabIndex = 23;
            button2.Text = "Cerrar";
            button2.UseVisualStyleBackColor = true;
            button2.Click += btnClose2_Click;
            // 
            // button3
            // 
            button3.Location = new Point(180, 429);
            button3.Name = "button3";
            button3.Size = new Size(94, 30);
            button3.TabIndex = 24;
            button3.Text = "Asistencia";
            button3.UseVisualStyleBackColor = true;
            button3.Click += btnAsistencia_Click;
            // 
            // frmDBVerify
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(335, 564);
            Controls.Add(button3);
            Controls.Add(button2);
            Controls.Add(button1);
            Controls.Add(cboReaders);
            Controls.Add(lblSelectReader);
            Controls.Add(lblPlaceFinger);
            Controls.Add(pbFingerprint);
            Margin = new Padding(4, 5, 4, 5);
            Name = "frmDBVerify";
            Text = "frmDBVerify";
            Load += frmDBVerify_Load;
            ((System.ComponentModel.ISupportInitialize)pbFingerprint).EndInit();
            ResumeLayout(false);

        }

        #endregion

        internal System.Windows.Forms.ComboBox cboReaders;
        internal System.Windows.Forms.Label lblSelectReader;
        internal System.Windows.Forms.Label lblPlaceFinger;
        internal System.Windows.Forms.PictureBox pbFingerprint;
        private Button button1;
        private Button button2;
        private Button button3;
    }
}