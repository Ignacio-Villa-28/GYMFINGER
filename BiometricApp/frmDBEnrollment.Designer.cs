namespace BiometricApp
{
    partial class frmDBEnrollment
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
            label1 = new Label();
            btnBack = new Button();
            lblPlaceFinger = new Label();
            pbFingerprint = new PictureBox();
            cboReaders = new ComboBox();
            lblSelectReader = new Label();
            button1 = new Button();
            button2 = new Button();
            cmbLedger = new ComboBox();
            ((System.ComponentModel.ISupportInitialize)pbFingerprint).BeginInit();
            SuspendLayout();
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(40, 58);
            label1.Margin = new Padding(4, 0, 4, 0);
            label1.Name = "label1";
            label1.Size = new Size(75, 20);
            label1.TabIndex = 1;
            label1.Text = "Ledger Id:";
            // 
            // btnBack
            // 
            btnBack.Location = new Point(194, 477);
            btnBack.Margin = new Padding(4, 5, 4, 5);
            btnBack.Name = "btnBack";
            btnBack.Size = new Size(75, 35);
            btnBack.TabIndex = 7;
            btnBack.Text = "Cerrar";
            btnBack.Click += btnBack_Click;
            // 
            // lblPlaceFinger
            // 
            lblPlaceFinger.Location = new Point(40, 363);
            lblPlaceFinger.Margin = new Padding(4, 0, 4, 0);
            lblPlaceFinger.Name = "lblPlaceFinger";
            lblPlaceFinger.Size = new Size(195, 29);
            lblPlaceFinger.TabIndex = 8;
            lblPlaceFinger.Text = "Place a finger on the reader";
            // 
            // pbFingerprint
            // 
            pbFingerprint.Location = new Point(43, 98);
            pbFingerprint.Margin = new Padding(4, 5, 4, 5);
            pbFingerprint.Name = "pbFingerprint";
            pbFingerprint.Size = new Size(212, 260);
            pbFingerprint.SizeMode = PictureBoxSizeMode.Zoom;
            pbFingerprint.TabIndex = 9;
            pbFingerprint.TabStop = false;
            // 
            // cboReaders
            // 
            cboReaders.Font = new Font("Tahoma", 8F);
            cboReaders.Location = new Point(43, 435);
            cboReaders.Margin = new Padding(4, 5, 4, 5);
            cboReaders.Name = "cboReaders";
            cboReaders.Size = new Size(211, 24);
            cboReaders.TabIndex = 16;
            // 
            // lblSelectReader
            // 
            lblSelectReader.Location = new Point(39, 411);
            lblSelectReader.Margin = new Padding(4, 0, 4, 0);
            lblSelectReader.Name = "lblSelectReader";
            lblSelectReader.Size = new Size(161, 20);
            lblSelectReader.TabIndex = 15;
            lblSelectReader.Text = "Select Reader:";
            // 
            // button1
            // 
            button1.Location = new Point(111, 477);
            button1.Margin = new Padding(4, 5, 4, 5);
            button1.Name = "button1";
            button1.Size = new Size(75, 35);
            button1.TabIndex = 7;
            button1.Text = "Guardar";
            button1.Click += button1_Click;
            // 
            // button2
            // 
            button2.Location = new Point(29, 477);
            button2.Name = "button2";
            button2.Size = new Size(75, 33);
            button2.TabIndex = 17;
            button2.Text = "Validar";
            button2.UseVisualStyleBackColor = true;
            button2.Click += btnVerify_Click;
            // 
            // cmbLedger
            // 
            cmbLedger.FormattingEnabled = true;
            cmbLedger.Location = new Point(124, 55);
            cmbLedger.Name = "cmbLedger";
            cmbLedger.Size = new Size(131, 28);
            cmbLedger.TabIndex = 18;
            // 
            // frmDBEnrollment
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(320, 526);
            Controls.Add(cmbLedger);
            Controls.Add(button2);
            Controls.Add(cboReaders);
            Controls.Add(lblSelectReader);
            Controls.Add(button1);
            Controls.Add(btnBack);
            Controls.Add(lblPlaceFinger);
            Controls.Add(pbFingerprint);
            Controls.Add(label1);
            Margin = new Padding(4, 5, 4, 5);
            Name = "frmDBEnrollment";
            Text = "frmDBEnrollment";
            FormClosing += frmDBEnrollment_FormClosing;
            Load += frmDBEnrollment_Load;
            ((System.ComponentModel.ISupportInitialize)pbFingerprint).EndInit();
            ResumeLayout(false);
            PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox txtLedgerId;
        private System.Windows.Forms.Label label1;
        internal System.Windows.Forms.Button btnBack;
        internal System.Windows.Forms.Label lblPlaceFinger;
        internal System.Windows.Forms.PictureBox pbFingerprint;
        internal System.Windows.Forms.ComboBox cboReaders;
        internal System.Windows.Forms.Label lblSelectReader;
        internal System.Windows.Forms.Button button1;
        private Button button2;
        private ComboBox cmbLedger;
    }
}