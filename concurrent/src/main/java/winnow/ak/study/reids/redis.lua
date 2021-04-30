local visitNum = redis.call('incr', KEYS[1])
if visitNum == 1 then  redis.call('expire', KEYS[1], ARGV[2]) end
if visitNum > tonumber(ARGV[1]) then return 0 end return 1