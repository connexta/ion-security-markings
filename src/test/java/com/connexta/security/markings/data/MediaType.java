/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.data;

public enum MediaType {
  // any
  ANY_TYPE(com.google.common.net.MediaType.ANY_TYPE.toString()),
  ANY_TEXT_TYPE(com.google.common.net.MediaType.ANY_TEXT_TYPE.toString()),
  ANY_IMAGE_TYPE(com.google.common.net.MediaType.ANY_IMAGE_TYPE.toString()),
  ANY_AUDIO_TYPE(com.google.common.net.MediaType.ANY_AUDIO_TYPE.toString()),
  ANY_VIDEO_TYPE(com.google.common.net.MediaType.ANY_VIDEO_TYPE.toString()),
  ANY_APPLICATION_TYPE(com.google.common.net.MediaType.ANY_APPLICATION_TYPE.toString()),

  // text
  CACHE_MANIFEST_UTF_8(com.google.common.net.MediaType.CACHE_MANIFEST_UTF_8.toString()),
  CSS_UTF_8(com.google.common.net.MediaType.CSS_UTF_8.toString()),
  CSV_UTF_8(com.google.common.net.MediaType.CSV_UTF_8.toString()),
  HTML_UTF_8(com.google.common.net.MediaType.HTML_UTF_8.toString()),
  I_CALENDAR_UTF_8(com.google.common.net.MediaType.I_CALENDAR_UTF_8.toString()),
  PLAIN_TEXT_UTF_8(com.google.common.net.MediaType.PLAIN_TEXT_UTF_8.toString()),
  TEXT_JAVASCRIPT_UTF_8(com.google.common.net.MediaType.TEXT_JAVASCRIPT_UTF_8.toString()),
  TSV_UTF_8(com.google.common.net.MediaType.TSV_UTF_8.toString()),
  VCARD_UTF_8(com.google.common.net.MediaType.VCARD_UTF_8.toString()),
  WML_UTF_8(com.google.common.net.MediaType.WML_UTF_8.toString()),
  XML_UTF_8(com.google.common.net.MediaType.XML_UTF_8.toString()),
  VTT_UTF_8(com.google.common.net.MediaType.VTT_UTF_8.toString()),

  // image
  BMP(com.google.common.net.MediaType.BMP.toString()),
  CRW(com.google.common.net.MediaType.CRW.toString()),
  GIF(com.google.common.net.MediaType.GIF.toString()),
  ICO(com.google.common.net.MediaType.ICO.toString()),
  JPEG(com.google.common.net.MediaType.JPEG.toString()),
  PNG(com.google.common.net.MediaType.PNG.toString()),
  PSD(com.google.common.net.MediaType.PSD.toString()),
  SVG_UTF_8(com.google.common.net.MediaType.SVG_UTF_8.toString()),
  TIFF(com.google.common.net.MediaType.TIFF.toString()),
  WEBP(com.google.common.net.MediaType.WEBP.toString()),
  HEIF(com.google.common.net.MediaType.HEIF.toString()),
  JP2K(com.google.common.net.MediaType.JP2K.toString()),

  // audio
  MP4_AUDIO(com.google.common.net.MediaType.MP4_AUDIO.toString()),
  MPEG_AUDIO(com.google.common.net.MediaType.MPEG_AUDIO.toString()),
  OGG_AUDIO(com.google.common.net.MediaType.OGG_AUDIO.toString()),
  WEBM_AUDIO(com.google.common.net.MediaType.WEBM_AUDIO.toString()),
  L16_AUDIO(com.google.common.net.MediaType.L16_AUDIO.toString()),
  L24_AUDIO(com.google.common.net.MediaType.L24_AUDIO.toString()),
  BASIC_AUDIO(com.google.common.net.MediaType.BASIC_AUDIO.toString()),
  AAC_AUDIO(com.google.common.net.MediaType.AAC_AUDIO.toString()),
  VORBIS_AUDIO(com.google.common.net.MediaType.VORBIS_AUDIO.toString()),
  WMA_AUDIO(com.google.common.net.MediaType.WMA_AUDIO.toString()),
  WAX_AUDIO(com.google.common.net.MediaType.WAX_AUDIO.toString()),
  VND_REAL_AUDIO(com.google.common.net.MediaType.VND_REAL_AUDIO.toString()),
  VND_WAVE_AUDIO(com.google.common.net.MediaType.VND_WAVE_AUDIO.toString()),

  // video
  MP4_VIDEO(com.google.common.net.MediaType.MP4_VIDEO.toString()),
  MPEG_VIDEO(com.google.common.net.MediaType.MPEG_VIDEO.toString()),
  OGG_VIDEO(com.google.common.net.MediaType.OGG_VIDEO.toString()),
  QUICKTIME(com.google.common.net.MediaType.QUICKTIME.toString()),
  WEBM_VIDEO(com.google.common.net.MediaType.WEBM_VIDEO.toString()),
  WMV(com.google.common.net.MediaType.WMV.toString()),
  FLV_VIDEO(com.google.common.net.MediaType.FLV_VIDEO.toString()),
  THREE_GPP_VIDEO(com.google.common.net.MediaType.THREE_GPP_VIDEO.toString()),
  THREE_GPP2_VIDEO(com.google.common.net.MediaType.THREE_GPP2_VIDEO.toString()),

  // application
  APPLICATION_XML_UTF_8(com.google.common.net.MediaType.APPLICATION_XML_UTF_8.toString()),
  ATOM_UTF_8(com.google.common.net.MediaType.ATOM_UTF_8.toString()),
  BZIP2(com.google.common.net.MediaType.BZIP2.toString()),
  DART_UTF_8(com.google.common.net.MediaType.DART_UTF_8.toString()),
  APPLE_PASSBOOK(com.google.common.net.MediaType.APPLE_PASSBOOK.toString()),
  EOT(com.google.common.net.MediaType.EOT.toString()),
  EPUB(com.google.common.net.MediaType.EPUB.toString()),
  FORM_DATA(com.google.common.net.MediaType.FORM_DATA.toString()),
  KEY_ARCHIVE(com.google.common.net.MediaType.KEY_ARCHIVE.toString()),
  APPLICATION_BINARY(com.google.common.net.MediaType.APPLICATION_BINARY.toString()),
  GEO_JSON(com.google.common.net.MediaType.GEO_JSON.toString()),
  GZIP(com.google.common.net.MediaType.GZIP.toString()),
  HAL_JSON(com.google.common.net.MediaType.HAL_JSON.toString()),
  JAVASCRIPT_UTF_8(com.google.common.net.MediaType.JAVASCRIPT_UTF_8.toString()),
  JOSE(com.google.common.net.MediaType.JOSE.toString()),
  JOSE_JSON(com.google.common.net.MediaType.JOSE_JSON.toString()),
  JSON_UTF_8(com.google.common.net.MediaType.JSON_UTF_8.toString()),
  MANIFEST_JSON_UTF_8(com.google.common.net.MediaType.MANIFEST_JSON_UTF_8.toString()),
  KML(com.google.common.net.MediaType.KML.toString()),
  KMZ(com.google.common.net.MediaType.KMZ.toString()),
  MBOX(com.google.common.net.MediaType.MBOX.toString()),
  APPLE_MOBILE_CONFIG(com.google.common.net.MediaType.APPLE_MOBILE_CONFIG.toString()),
  MICROSOFT_EXCEL(com.google.common.net.MediaType.MICROSOFT_EXCEL.toString()),
  MICROSOFT_OUTLOOK(com.google.common.net.MediaType.MICROSOFT_OUTLOOK.toString()),
  MICROSOFT_POWERPOINT(com.google.common.net.MediaType.MICROSOFT_POWERPOINT.toString()),
  MICROSOFT_WORD(com.google.common.net.MediaType.MICROSOFT_WORD.toString()),
  WASM_APPLICATION(com.google.common.net.MediaType.WASM_APPLICATION.toString()),
  NACL_APPLICATION(com.google.common.net.MediaType.NACL_APPLICATION.toString()),
  NACL_PORTABLE_APPLICATION(com.google.common.net.MediaType.NACL_PORTABLE_APPLICATION.toString()),
  OCTET_STREAM(com.google.common.net.MediaType.OCTET_STREAM.toString()),
  OGG_CONTAINER(com.google.common.net.MediaType.OGG_CONTAINER.toString()),
  OOXML_DOCUMENT(com.google.common.net.MediaType.OOXML_DOCUMENT.toString()),
  OOXML_PRESENTATION(com.google.common.net.MediaType.OOXML_PRESENTATION.toString()),
  OOXML_SHEET(com.google.common.net.MediaType.OOXML_SHEET.toString()),
  OPENDOCUMENT_GRAPHICS(com.google.common.net.MediaType.OPENDOCUMENT_GRAPHICS.toString()),
  OPENDOCUMENT_PRESENTATION(com.google.common.net.MediaType.OPENDOCUMENT_PRESENTATION.toString()),
  OPENDOCUMENT_SPREADSHEET(com.google.common.net.MediaType.OPENDOCUMENT_SPREADSHEET.toString()),
  OPENDOCUMENT_TEXT(com.google.common.net.MediaType.OPENDOCUMENT_TEXT.toString()),
  PDF(com.google.common.net.MediaType.PDF.toString()),
  POSTSCRIPT(com.google.common.net.MediaType.POSTSCRIPT.toString()),
  PROTOBUF(com.google.common.net.MediaType.PROTOBUF.toString()),
  RDF_XML_UTF_8(com.google.common.net.MediaType.RDF_XML_UTF_8.toString()),
  RTF_UTF_8(com.google.common.net.MediaType.RTF_UTF_8.toString()),
  SFNT(com.google.common.net.MediaType.SFNT.toString()),
  SHOCKWAVE_FLASH(com.google.common.net.MediaType.SHOCKWAVE_FLASH.toString()),
  SKETCHUP(com.google.common.net.MediaType.SKETCHUP.toString()),
  SOAP_XML_UTF_8(com.google.common.net.MediaType.SOAP_XML_UTF_8.toString()),
  TAR(com.google.common.net.MediaType.TAR.toString()),
  WOFF(com.google.common.net.MediaType.WOFF.toString()),
  WOFF2(com.google.common.net.MediaType.WOFF2.toString()),
  XHTML_UTF_8(com.google.common.net.MediaType.XHTML_UTF_8.toString()),
  XRD_UTF_8(com.google.common.net.MediaType.XRD_UTF_8.toString()),
  ZIP(com.google.common.net.MediaType.ZIP.toString()),
  ;

  private String value;

  MediaType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
