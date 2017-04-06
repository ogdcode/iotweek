require 'net/http'
require 'net/https'
require 'uri'
require 'json'

BASE_URL = "https://api.mlab.com:443/api/1/databases/iotweek-db/collections"
API_KEY = "gUBeJJih5LDEcH9jLV3duXYRmx-Rw7dJ"

def does_id_exist(id)
	id = JSON.parse(id)['card_id']
	id = URI.escape("{\"card_id\": \"#{id}\"}")
	uri = URI.parse("#{BASE_URL}/cards?fo=true&q=#{id}&apiKey=#{API_KEY}")

	# Create the HTTPS object
	https = Net::HTTP.new(uri.host, uri.port)
	https.use_ssl = true

	# Create the request object
	req = Net::HTTP::Get.new(uri.request_uri)

	res = https.request(req)
	body = JSON.parse(res.body)

	if body.nil?
		return nil
	else
		return JSON.dump(body['card_id'])
	end
end

def authorize(id)
	attempt = {'card_id' => id, 'timestamp' => Time.now.to_i, 'authorized' => 'false'}
	exists = does_id_exist(attempt.to_json)
	if !exists.nil?
		attempt['authorized'] = 'true'
	end

	send_api(attempt, "attempts")

	return attempt['authorized'].eql? 'true'
end

def send_api(data, collection)
	uri = URI.parse("#{BASE_URL}/#{collection}?apiKey=#{API_KEY}")
	header = {'Content-Type' => 'application/json'}

	# Create the HTTPS object
	https = Net::HTTP.new(uri.host, uri.port)
	https.use_ssl = true

	# Create the request object
	req = Net::HTTP::Post.new(uri.request_uri, header)
	req.body = data.to_json

	# Send the request
	res = https.request(req)

	return res.code
end

#data = { 'id' => 'cafebebe', 'timestamp' => 123456789, 'authorized' => 'false' }

#send_api({ 'card_id' => 'cafebebe', 'card_name' => 'Jean-Paul'}, "cards")
