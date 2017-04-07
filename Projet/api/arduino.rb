# Simple Ruby program to read from Arduino serial.
# Uses the SerialPort gem (http://rubygems.org/gems/serialport)

require 'serialport'
require_relative 'server.rb'

# Parameters for serial port connection
port_str = "#{ARGV[0]}"
baud_rate = 9600
data_bits = 8
stop_bits = 1
parity = SerialPort::NONE

sp = SerialPort.new(port_str, baud_rate, data_bits, stop_bits, parity)
sp.sync = true

# Just read forever
while true do
   while (data = sp.gets.chomp) do
       puts "Received request for card id #{data}"
       sp.write(authorize(data) == true ? "AUTHORIZED\n" : "UNAUTHORIZED\n")
    end
end

sp.close
