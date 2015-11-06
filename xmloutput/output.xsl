<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>modele.xsl</title>
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootswatch/3.3.5/lumen/bootstrap.min.css"></link>
            </head>
            <body>
                
	<div class="container">
            
            <h1>
		<xsl:variable name="path" select="episodes/episode/image"/><a href="{lien}"><img src="{$path}"/></a>		
                <hr/>Serie : <xsl:value-of select="episodes/episode/titre"/>
                
            </h1>
                
            <hr/>
                
            <table class="table">
            <tr>
              <th style="text-align:left">titreComplet</th>
              <th style="text-align:left">saison</th>
              <th style="text-align:left">qualite</th>
              <th style="text-align:left">lien</th>
              <th style="text-align:left">numero</th>
              <th style="text-align:left">description</th>
              <th style="text-align:left">note</th>
              <th style="text-align:left">torrent</th>
            </tr>
            <xsl:for-each select="episodes/episode">
            <tr>
              <td><xsl:value-of select="titreComplet" /></td>
              <td><xsl:value-of select="saison" /></td>
              <td><xsl:value-of select="qualite" /></td>
              <td><xsl:value-of select="lien" /></td>
              <td><xsl:value-of select="numero" /></td>
              <td><xsl:value-of select="description" /></td>
              <td><xsl:value-of select="note" /></td>
              <td> 
                  <ul class="list-group">
                      <li>
                         infoHash : <xsl:value-of select="torrent/infoHash" />
                      </li>
                      <li>
                         magnetURI :<xsl:value-of select="torrent/magnetURI" />
                      </li>
                      <li>
                         seeds : <xsl:value-of select="torrent/seeds" />
                      </li>
                      <li>
                         peers : <xsl:value-of select="torrent/peers" />
                      </li>
                      <li>
                          verified :
                          <xsl:value-of select="torrent/verified" />
                      </li>
                      <li>
                          filename : <xsl:value-of select="torrent/filename" />
                      </li>
                      <li>
                          enclosure : <xsl:value-of select="torrent/enclosure" />
                      </li>
                  </ul>
              </td>
            
            </tr>
            
            
            </xsl:for-each>
            
          </table>

	</div>
        
        
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
