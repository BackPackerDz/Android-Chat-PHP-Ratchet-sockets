<?php
namespace MyApp;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;

class Chat implements MessageComponentInterface {
    protected $clients;

    public function __construct() {
        $this->clients = new \SplObjectStorage;
    }

    public function onOpen(ConnectionInterface $conn) {
        // une nouvelle connexion, on l'enregistre !
        $this->clients->attach($conn);


        echo "Un nouvel utilisateur ! ({$conn->resourceId})\n";
    }

    public function onMessage(ConnectionInterface $from, $msg) {

        $numRecv = count($this->clients) - 1;
        echo sprintf('Connection %d a envoyé un message "%s" to %d aux autres connection%s' . "\n"
            , $from->resourceId, $msg, $numRecv, $numRecv == 1 ? '' : 's');

        /*
         * On envoit le message à tous les autres utilisateur sauf nous même 
         *
         */
        foreach ($this->clients as $client) {
            if ($from !== $client) {
                $client->send($msg);
            }
        }
    }

    public function onClose(ConnectionInterface $conn) {
        // connexion fermé, on se débarasse du client
        $this->clients->detach($conn);

        echo "L'utilisateur {$conn->resourceId} s'est déconnecté\n";
    }

    public function onError(ConnectionInterface $conn, \Exception $e) {
        echo "Une erreur s'est produite : {$e->getMessage()}\n";

        $conn->close();
    }
}