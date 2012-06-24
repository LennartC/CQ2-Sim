// Copyright (c) 2005-2008 Thomas Fuchs (http://script.aculo.us, http://mir.aculo.us)
//
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// For details, see the script.aculo.us web site: http://script.aculo.us/

var Scriptaculous = {
  Version: '1.8.2',
  require: function(libraryName) {
    try{
      // inserting via DOM fails in Safari 2.0, so brute force approach
      document.write('<script type="text/javascript" src="'+libraryName+'"><\/script>');
    } catch(e) {
      // for xhtml+xml served content, fall back to DOM methods
      var script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = libraryName;
      document.getElementsByTagName('head')[0].appendChild(script);
    }
  },
  REQUIRED_PROTOTYPE: '1.6.0.3',
  load: function() {
    function convertVersionString(versionString) {
      var v = versionString.replace(/_.*|\./g, '');
      v = parseInt(v + '0'.times(4-v.length));
      return versionString.indexOf('_') > -1 ? v-1 : v;
    }

    if((typeof Prototype=='undefined') ||
       (typeof Element == 'undefined') ||
       (typeof Element.Methods=='undefined') ||
       (convertVersionString(Prototype.Version) <
        convertVersionString(Scriptaculous.REQUIRED_PROTOTYPE)))
       throw("script.aculo.us requires the Prototype JavaScript framework >= " +
        Scriptaculous.REQUIRED_PROTOTYPE);

    var js = /scriptaculous\.js(\?.*)?$/;
    $$('head script[src]').findAll(function(s) {
      return s.src.match(js);
    }).each(function(s) {
      var path = s.src.replace(js, ''),
      includes = s.src.match(/\?.*load=([a-z,]*)/);
      (includes ? includes[1] : 'builder,effects,dragdrop,controls,slider,sound').split(',').each(
       function(include) { Scriptaculous.require(path+include+'.js') });
    });
  }
};

Scriptaculous.load();

var oldReport = "";

function handleShowReport(rep) {
	var divId = rep[0];
	var report = rep[1];
	
	document.getElementById(divId).innerHTML = report;

	Effect.SlideDown(divId, { duration: 0.3 });
}

function showreport(id, report) {
	if (document.getElementById(report).style.display == 'none') {
		SimAjax.getRaidReport(id,report,handleShowReport);
	} else {
		Effect.SlideUp(report, { duration: 0.3 });
	}
}

function showlikes(like) {
	if (document.getElementById(like).style.display == 'none') {
		Effect.SlideDown(like, { duration: 0.3 });
	} else {
		Effect.SlideUp(like, { duration: 0.3 });
	}
}

var oldComment = "";

function showcommentform(commentfrm) {
	if (oldComment != "") {
		Effect.SlideUp(oldComment, { duration: 0.3 });
		if (oldComment != commentfrm) {
			oldComment=commentfrm;
			setTimeout('Effect.SlideDown(\''+commentfrm+'\', { duration: 0.3 });',320);
		} else {
			oldComment="";
		}
	} else {
		if (oldComment != commentfrm) {
			oldComment=commentfrm;
			Effect.SlideDown(commentfrm, { duration: 0.3 });
		} else {
			oldComment="";
		}
	}
}

function handleShowReveal(rep) {
	var divId = rep[0];
	var reveal = rep[1];
	
	document.getElementById(divId).innerHTML = reveal;

	Effect.SlideDown(divId, { duration: 0.3 });
}

function showreveal(link, reveal) {
	if (document.getElementById(reveal).style.display == 'none') {
		link.innerHTML = 'hide reveal';
		Effect.SlideDown(reveal, { duration: 0.3 });
	} else {
		link.innerHTML = 'show reveal';
		Effect.SlideUp(reveal, { duration: 0.3 });
	}
}