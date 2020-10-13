<?php
/**
 * Log-Class
 *
 * @package "utils"
 */
class logger {

    /**
     *
     */
    const LOG_FILE = 'schaden.log';

    /**
     * -------------------------------------------------------------------
     * Methode to log a message
     *
     * @param string $message
     * @param string $level
     */
    public static function log(string $message, string $level = 'notice') {
        $log_data = [
            'message' => $message,
            'level'   => $level,
        ];
        
        self::write($log_data);
    }

    protected static function write(array $log_data_plain) {
        $file_handler = fopen('/srv/schaden/logs/' . self::LOG_FILE, 'a');
        fputs($file_handler, $log_data_plain . "\n");
        fclose($file_handler);
    }
}
