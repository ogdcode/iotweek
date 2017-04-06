#simplest ruby program to read from arduino serial,
#using the SerialPort gem
#(http://rubygems.org/gems/serialport)

require "serialport"
require_relative "server.rb"

params for serial port
port_str = "#{ARGV[0]}"
baud_rate = 9600
data_bits = 8
stop_bits = 1
parity = SerialPort::NONE

sp = SerialPort.new(port_str, baud_rate, data_bits, stop_bits, parity)

#just read forever
while true do
   while (data = sp.gets.chomp) do
      authorize(data)
    end
end

sp.close
