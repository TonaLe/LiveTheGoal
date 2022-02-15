package module.account.Event.ErrorProducer;

import module.account.DTO.ErrorDto;

/**
 * The interface Error producer.
 */
public interface ErrorProducer {

    /**
     * Send message.
     *
     * @param errorDto the error dto
     */
    void sendMessage(final ErrorDto errorDto);
}
