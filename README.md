# Network--assingment1
This is a study representation of a udp-protocol created to run on docker containers. Client - Server - Worker(s) communication 

The focus of this work is to learn about protocol development and the information that is kept
in a header to support functionality of a protocol. The aim of the protocol is to provide a mechanism to retrieve files from a service based on UDP datagrams.
The encoding of the header information of this protocol is implemented in a binary format.
The protocol involves a number of actors: One or more clients, an ingress node, and one or more workers.
A client issues requests for files to an ingress node and receives replies from this node. The server node
processes requests, forwards them to one of the workers that are associated with it, and forwards replies to
clients that have send them. The header information included in the packets has to support the
identification of the requested action, the transfer of files - potentially consisting of a number of packets and
the management of the workers by the server.
