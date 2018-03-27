package com.sydney.dream.elasticplugin;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Term;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ScriptPlugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;
import org.elasticsearch.script.*;
import org.elasticsearch.search.lookup.SearchLookup;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyNativeScriptPlugin extends Plugin implements ScriptPlugin {

    @Override
    public ScriptEngineService getScriptEngineService(Settings settings) {
        return new MyExpertScriptEngine();
    }

    private static class MyExpertScriptEngine implements ScriptEngineService {
        @Override
        public String getType() {
            return "expert_scripts";
        }


        @Override
        public Function<Map<String,Object>,SearchScript> compile(String scriptName,
                                                                 String scriptSource,
                                                                 Map<String, String> params) {
            // we use the script "source" as the script identifier
            if ("pure_df".equals(scriptSource)) {
                return p -> new SearchScript() {
                    final String field;
                    final String term;
                    {
                        if (p.containsKey("field") == false) {
                            throw new IllegalArgumentException("Missing parameter [field]");
                        }
                        if (p.containsKey("term") == false) {
                            throw new IllegalArgumentException("Missing parameter [term]");
                        }
                        field = p.get("field").toString();
                        term = p.get("term").toString();
                    }

                    @Override
                    public LeafSearchScript getLeafSearchScript(LeafReaderContext context)
                            throws IOException {
                        PostingsEnum postings = context.reader().postings(new Term(field, term));
                        if (postings == null) {
                            // the field and/or term don't exist in this segment, so always return 0
                            return () -> 0.0d;
                        }
                        return new LeafSearchScript() {
                            int currentDocid = -1;
                            @Override
                            public void setDocument(int docid) {
                                // advance has undefined behavior calling with a docid <= its current docid
                                if (postings.docID() < docid) {
                                    try {
                                        postings.advance(docid);
                                    } catch (IOException e) {
                                        throw new UncheckedIOException(e);
                                    }
                                }
                                currentDocid = docid;
                            }
                            @Override
                            public double runAsDouble() {

                                return 0.5;
                            }
                        };
                    }

                    @Override
                    public boolean needsScores() {
                        return false;
                    }
                };
            }
            throw new IllegalArgumentException("Unknown script name " + scriptSource);
        }

        @Override
        @SuppressWarnings("unchecked")
        public SearchScript search(CompiledScript compiledScript,
                                   SearchLookup lookup,
                                   @Nullable Map<String, Object> params) {
            Function<Map<String,Object>,SearchScript> scriptFactory = (Function<Map<String,Object>,
                    SearchScript>) compiledScript.compiled();
            return scriptFactory.apply(params);
        }

        @Override
        public ExecutableScript executable(CompiledScript compiledScript,
                                           @Nullable Map<String, Object> params) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isInlineScriptEnabled() {
            return true;
        }

        @Override
        public void close() {}
    }
}