from serial import Serial
serial_port = Serial(port='/dev/tty.usbmodem1413', baudrate=9600)
while True:
	print serial_port.readline()
