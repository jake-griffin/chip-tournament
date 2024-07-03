package jg.chip.adapter;

public interface Adapter<I, O> {
    O adapt(I input, Long currentId);
}
