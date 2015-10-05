#cs512 Project deliverable 1

##Prerequisites
- JDK (7+)
- Apache Ant

###Resource Managers
```ant rm1 -Dservice.rm.host=HOSTNAME -Dservice.rm.port=PORT```

```ant rm2 -Dservice.rm2.host=HOSTNAME -Dservice.rm2.port=PORT```

```ant rm3 -Dservice.rm3.host=HOSTNAME -Dservice.rm3.port=PORT```

By default without arguments, the hostname is localhost and the ports are 8088, 8089, 8090. 

To launch all 3 resource managers with default values, do ```ant rm```

Access via: http://HOSTNAME:PORT/rm/rm



###Middleware 
```ant server -Dservice.port=PORT```

will launch middleware and it will use default values to find the resource managers.  By default, middleware's port is 8080.

```ant server -Dservice.rm.host=HOSTNAME -Dservice.rm.port=PORT -Dservice.rm2.host=HOSTNAME -Dservice.rm2.port=PORT -Dservice.rm3.host=HOSTNAME -Dservice.rm3.port=PORT```

will launch middleware with specified resource manager hostnames and port numbers.

Access it via: http://HOSTNAME:PORT/mw/service

###Client
```ant client -Dservice.host=HOST -Dservice.port=PORT```

Where HOST is the hostname/IP of the middleware and PORT is the port that the middleware uses.

--

Refer to README.txt for more information
