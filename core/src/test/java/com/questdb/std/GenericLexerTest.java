/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2019 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.questdb.std;

import com.questdb.griffin.SqlUtil;
import com.questdb.std.str.StringSink;
import com.questdb.test.tools.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class GenericLexerTest {

    @Test
    public void testBlockComments() {
        GenericLexer lex = new GenericLexer(64);
        lex.defineSymbol("+");
        lex.defineSymbol("++");
        lex.defineSymbol("*");
        lex.defineSymbol("/*");
        lex.defineSymbol("*/");

        lex.of("a + /* ok, this /* is a */ comment */ 'b' * abc");

        StringSink sink = new StringSink();
        CharSequence token;
        while ((token = SqlUtil.fetchNext(lex)) != null) {
            sink.put(token);
        }

        TestUtils.assertEquals("a+'b'*abc", sink);
    }

    @Test
    public void testEdgeSymbol() {
        GenericLexer ts = new GenericLexer(64);
        ts.defineSymbol(" ");
        ts.defineSymbol("+");
        ts.defineSymbol("(");
        ts.defineSymbol(")");
        ts.defineSymbol(",");

        CharSequence content;
        ts.of(content = "create journal xyz(a int, b int)");
        StringSink sink = new StringSink();
        for (CharSequence cs : ts) {
            sink.put(cs);
        }
        TestUtils.assertEquals(content, sink);
    }

    @Test
    public void testLineComment() {
        GenericLexer lex = new GenericLexer(64);
        lex.defineSymbol("+");
        lex.defineSymbol("++");
        lex.defineSymbol("*");
        lex.defineSymbol("/*");
        lex.defineSymbol("*/");
        lex.defineSymbol("--");

        lex.of("a + -- ok, this is a comment \n 'b' * abc");

        StringSink sink = new StringSink();
        CharSequence token;
        while ((token = SqlUtil.fetchNext(lex)) != null) {
            sink.put(token);
        }

        TestUtils.assertEquals("a+'b'*abc", sink);
    }

    @Test
    public void testNullContent() {
        GenericLexer ts = new GenericLexer(64);
        ts.defineSymbol(" ");
        ts.of(null);
        Assert.assertFalse(ts.iterator().hasNext());
    }

    @Test
    public void testQuotedToken() {
        GenericLexer ts = new GenericLexer(64);
        ts.defineSymbol("+");
        ts.defineSymbol("++");
        ts.defineSymbol("*");

        ts.of("a+\"b\"*abc");

        StringSink sink = new StringSink();
        for (CharSequence cs : ts) {
            sink.put(cs);
        }

        TestUtils.assertEquals("a+\"b\"*abc", sink);
    }

    @Test
    public void testSingleQuotedToken() {
        GenericLexer ts = new GenericLexer(64);
        ts.defineSymbol("+");
        ts.defineSymbol("++");
        ts.defineSymbol("*");

        ts.of("a+'b'*abc");

        StringSink sink = new StringSink();
        for (CharSequence cs : ts) {
            sink.put(cs);
        }

        TestUtils.assertEquals("a+'b'*abc", sink);
    }

    @Test
    public void testSymbolLookup() {
        GenericLexer ts = new GenericLexer(64);
        ts.defineSymbol("+");
        ts.defineSymbol("++");
        ts.defineSymbol("*");

        CharSequence content;
        ts.of(content = "+*a+b++blah-");

        StringSink sink = new StringSink();
        for (CharSequence cs : ts) {
            sink.put(cs);
        }
        TestUtils.assertEquals(content, sink);
    }

    @Test
    public void testUnparse() {
        GenericLexer ts = new GenericLexer(64);
        ts.defineSymbol("+");
        ts.defineSymbol("++");
        ts.defineSymbol("*");
        ts.of("+*a+b++blah-");

        Iterator<CharSequence> it = ts.iterator();

        while (it.hasNext()) {
            CharSequence e = it.next();
            ts.unparse();
            CharSequence a = it.next();
            TestUtils.assertEquals(e, a);
        }
    }
}
