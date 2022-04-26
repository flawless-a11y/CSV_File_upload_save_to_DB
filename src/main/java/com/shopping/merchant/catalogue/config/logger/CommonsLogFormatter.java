package com.shopping.merchant.catalogue.config.logger;

import com.shopping.merchant.catalogue.jwt.JwtUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zalando.logbook.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

import static java.time.temporal.ChronoField.*;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Common_Log_Format">Wikipedia: Common Log Format</a>
 * @see <a href="https://httpd.apache.org/docs/trunk/logs.html#common">Apache HTTP Server: Common Log Format</a>
 */

public final class CommonsLogFormatter extends JwtUtils implements Sink {
    /*@Autowired
    private JwtUtils jwtUtils;*/
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendLiteral('[')
            .appendValue(DAY_OF_MONTH, 2)
            .appendLiteral('/')
            .appendText(MONTH_OF_YEAR, TextStyle.SHORT)
            .appendLiteral('/')
            .appendValue(YEAR, 4)
            .appendLiteral(':')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .appendLiteral(' ')
            .appendOffset("+HHMM", "0000")
            .appendLiteral(']')
            .toFormatter();

    private final HttpLogWriter writer;
    private final ZoneId timeZone;

    public CommonsLogFormatter(final HttpLogWriter writer) {
        this(writer, ZoneId.systemDefault());
    }

    public CommonsLogFormatter(HttpLogWriter writer, ZoneId timeZone) {
        this.writer = writer;
        this.timeZone = timeZone;
    }

    @Override
    public boolean isActive() {
        return writer.isActive();
    }

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request) {
        // nothing to do...
    }

    @Override
    public void write(final Correlation correlation, final HttpRequest request,
                      final HttpResponse response) throws IOException {

        final StringBuilder output = new StringBuilder(120);
        HttpServletRequest request1 =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        output.append(getClaims(request1.getHeader("Authorization").substring(7)));
        output.append(' ');
        output.append(request.getRemote());
        output.append(' ');
        output.append('-');
        output.append(' ');
        output.append('-');
        output.append(' ');
        formatter.formatTo(correlation.getStart().atZone(timeZone), output);
        output.append(' ');
        output.append('"');
        {
            output.append(request.getMethod());
            output.append(' ');
            output.append(request.getPath());

            final String query = request.getQuery();

            if (!query.isEmpty()) {
                output.append('?');
                output.append(query);
            }

            output.append(' ');
            output.append(request.getProtocolVersion());
        }
        output.append('"');

        output.append(' ');
        output.append(response.getStatus());
        output.append(' ');

        final int bytes = response.getBody().length;

        if (bytes == 0) {
            output.append('-');
        } else {
            output.append(bytes);
        }

        writer.write(correlation, output.toString());
    }

}