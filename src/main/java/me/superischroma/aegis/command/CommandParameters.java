package me.superischroma.aegis.command;

import me.superischroma.aegis.rank.Rank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters
{
    String description() default "";
    String usage() default "/<command>";
    String aliases() default "";
    CommandSource source() default CommandSource.ANY;
    Rank rank() default Rank.DEFAULT;
    boolean root() default false;
}
