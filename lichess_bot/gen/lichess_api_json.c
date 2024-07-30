#include "jsmn.h"
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#ifdef  __cplusplus
extern	"C"	{
#endif

typedef unsigned char bool;

// local parser data area
#define TOKCOUNT 10000
static char json_buffer_space[1000000];
static char * json_buffer = json_buffer_space;
static jsmntok_t t[TOKCOUNT];
static jsmn_parser p; // JSON parser structure

static int jsoneq(const char *json, jsmntok_t *tok, const char *s) {
  if (tok->type == JSMN_STRING && (int)strlen(s) == tok->end - tok->start &&
      strncmp(json + tok->start, s, tok->end - tok->start) == 0) {
    return 0;
  }
  return -1;
}

/*
 * Dump formatted JSON.
 */
static int dump(const char *js, jsmntok_t *t, size_t count, int indent) {
  int i, j, k;
  jsmntok_t *key;
  if (count == 0) {
    return 0;
  }
  if (t->type == JSMN_PRIMITIVE) {
    printf("%.*s", t->end - t->start, js + t->start);
    return 1;
  } else if (t->type == JSMN_STRING) {
    printf("\"%.*s\"", t->end - t->start, js + t->start);
    return 1;
  } else if (t->type == JSMN_OBJECT) {
    printf("{\n");
    j = 0;
    for (i = 0; i < t->size; i++) {
      for (k = 0; k < indent; k++) {
        printf("  ");
      }
      key = t + 1 + j;
      j += dump(js, key, count - j, indent + 1);
      if (key->size > 0) {
        printf(": ");
        j += dump(js, t + 1 + j, count - j, indent + 1);
      }
      if (i < (t->size-1)) {
        printf(",\n");
      }
    }
    printf("}\n");
    return j + 1;
  } else if (t->type == JSMN_ARRAY) {
    j = 0;
    printf("[\n");
    for (i = 0; i < t->size; i++) {
      for (k = 0; k < indent - 1; k++) {
        printf("  ");
      }
      printf("   - ");
      j += dump(js, t + 1 + j, count - j, indent + 1);
      printf("\n");
    }
    printf("]\n");
    return j + 1;
  }
  return 0;
}

/*
 * Read the named file and populate the JSON buffer.
 */
int read_file_into_buffer( char * );
int read_file_into_buffer( char * filename )
{
  int bytelength = 0;
  FILE * f = fopen( filename, "r" );

  if ( f ) {
    fseek (f, 0, SEEK_END);
    bytelength = ftell (f);
    fseek (f, 0, SEEK_SET);
    if (json_buffer) {
      fread (json_buffer, 1, bytelength, f);
    }
    fclose (f);
  }
  return bytelength;
}

/*
 * Initialize the parser and parse the buffer into JSON tokens.
 */
int init_parser_and_parse( int );
int init_parser_and_parse( int bytelength )
{
  int token_count;
  /* Prepare parser */
  jsmn_init(&p);
  // Parse the JSON string.
  token_count = jsmn_parse(&p, json_buffer, bytelength, t, TOKCOUNT);
  return token_count;
}

void api_connected( const int, const int );
void api_connected( const int starting_token_offset, const int token_count )
{
  /* No arguments to this command.  */
}

void api_connect( const int, const int );
void api_connect( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char api_base_url[1024];
  char access_token[1024];
  int max_games;
  bool auto_open_challenge_url;
  bool enable_debug_logging;
  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if (jsoneq(json_buffer, &t[i], "api_base_url") == 0) {
      strncpy( api_base_url, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      api_base_url[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "access_token") == 0) {
      strncpy( access_token, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      access_token[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "max_games") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      max_games = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "auto_open_challenge_url") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      auto_open_challenge_url = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "enable_debug_logging") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      enable_debug_logging = (s[0] == 't') ? 1 : 0;
      i++;
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
  }
}

void api_challenge( const int, const int );
void api_challenge( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  /* challenge */
    char challenge_id[1024];
    char challenge_url[1024];
    char challenge_status[1024];
    /* challenger */
      char challenger_id[1024];
      char challenger_name[1024];
      int challenger_rating;
      bool challenger_provisional;
      bool challenger_online;
      bool challenger_patron;
      char challenger_title[1024];
    /* destUser */
      char destUser_id[1024];
      char destUser_name[1024];
      int destUser_rating;
      bool destUser_provisional;
      bool destUser_online;
      bool destUser_patron;
      char destUser_title[1024];
    /* variant */
      char variant_key[1024];
    bool challenge_rated;
    char challenge_speed[1024];
    /* timeControl */
      char timeControl_type[1024];
      int timeControl_limit;
      int timeControl_increment;
      int timeControl_daysPerTurn;
      char timeControl_show[1024];
    char challenge_color[1024];
    char challenge_initialFen[1024];
    char challenge_declineReason[1024];
    char challenge_declineReasonKey[1024];

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if (jsoneq(json_buffer, &t[i], "challenge") == 0) {
      strcpy( object_scope, "challenge" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "id") == 0) {
      if (0 == strcmp(object_scope, "challenge")) {
        strncpy( challenge_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenge_id[len] = 0;
      } else if (0 == strcmp(object_scope, "challenger")) {
        strncpy( challenger_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenger_id[len] = 0;
      } else if (0 == strcmp(object_scope, "destUser")) {
        strncpy( destUser_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        destUser_id[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "url") == 0) {
      strncpy( challenge_url, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_url[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "status") == 0) {
      strncpy( challenge_status, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_status[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "challenger") == 0) {
      strcpy( object_scope, "challenger" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "destUser") == 0) {
      strcpy( object_scope, "destUser" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "name") == 0) {
      if (0 == strcmp(object_scope, "challenger")) {
        strncpy( challenger_name, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenger_name[len] = 0;
      } else if (0 == strcmp(object_scope, "destUser")) {
        strncpy( destUser_name, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        destUser_name[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "rating") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      if (0 == strcmp(object_scope, "challenger")) {
        challenger_rating = atoi(s);
      } else if (0 == strcmp(object_scope, "destUser")) {
        destUser_rating = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "provisional") == 0) {
      if (0 == strcmp(object_scope, "challenger")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenger_provisional = (s[0] == 't') ? 1 : 0;
      } else if (0 == strcmp(object_scope, "destUser")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        destUser_provisional = (s[0] == 't') ? 1 : 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "online") == 0) {
      if (0 == strcmp(object_scope, "challenger")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenger_online = (s[0] == 't') ? 1 : 0;
      } else if (0 == strcmp(object_scope, "destUser")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        destUser_online = (s[0] == 't') ? 1 : 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "patron") == 0) {
      if (0 == strcmp(object_scope, "challenger")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenger_patron = (s[0] == 't') ? 1 : 0;
      } else if (0 == strcmp(object_scope, "destUser")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        destUser_patron = (s[0] == 't') ? 1 : 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "title") == 0) {
      if (0 == strcmp(object_scope, "challenger")) {
        strncpy( challenger_title, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        challenger_title[len] = 0;
      } else if (0 == strcmp(object_scope, "destUser")) {
        strncpy( destUser_title, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        destUser_title[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "variant") == 0) {
      strcpy( object_scope, "variant" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "key") == 0) {
      if (0 == strcmp(object_scope, "variant")) {
        strncpy( variant_key, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        variant_key[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "rated") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_rated = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "speed") == 0) {
      strncpy( challenge_speed, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_speed[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "timeControl") == 0) {
      strcpy( object_scope, "timeControl" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "type") == 0) {
      if (0 == strcmp(object_scope, "timeControl")) {
        strncpy( timeControl_type, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        timeControl_type[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "limit") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      if (0 == strcmp(object_scope, "timeControl")) {
        timeControl_limit = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "increment") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      if (0 == strcmp(object_scope, "timeControl")) {
        timeControl_increment = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "daysPerTurn") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      if (0 == strcmp(object_scope, "timeControl")) {
        timeControl_daysPerTurn = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "show") == 0) {
      if (0 == strcmp(object_scope, "timeControl")) {
        strncpy( timeControl_show, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        timeControl_show[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "color") == 0) {
      strncpy( challenge_color, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_color[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "initialFen") == 0) {
      strncpy( challenge_initialFen, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_initialFen[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "declineReason") == 0) {
      strncpy( challenge_declineReason, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_declineReason[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "declineReasonKey") == 0) {
      strncpy( challenge_declineReasonKey, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      challenge_declineReasonKey[len] = 0;
      i++;
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
  }
}

void api_gameStart( const int, const int );
void api_gameStart( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  /* game_event */
    char game_event_id[1024];
    char game_event_source[1024];
    /* status */
      int status_id;
      char status_name[1024];

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if (jsoneq(json_buffer, &t[i], "game_event") == 0) {
      strcpy( object_scope, "game_event" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "status") == 0) {
      strcpy( object_scope, "status" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "id") == 0) {
      if (0 == strcmp(object_scope, "game_event")) {
        strncpy( game_event_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        game_event_id[len] = 0;
      } else if (0 == strcmp(object_scope, "status")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        status_id = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "source") == 0) {
      if (0 == strcmp(object_scope, "game_event")) {
        strncpy( game_event_source, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        game_event_source[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "name") == 0) {
      if (0 == strcmp(object_scope, "status")) {
        strncpy( status_name, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        status_name[len] = 0;
      }
      i++;
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
  }
}

void api_gameFull( const int, const int );
void api_gameFull( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  /* game */
    char game_id[1024];
    /* variant */
      char variant_key[1024];
    char game_speed[1024];
    bool game_rated;
    int game_createdAt;
    /* white */
      char white_id[1024];
      char white_name[1024];
      int white_rating;
      bool white_provisional;
      bool white_online;
      bool white_patron;
      char white_title[1024];
    /* black */
      char black_id[1024];
      char black_name[1024];
      int black_rating;
      bool black_provisional;
      bool black_online;
      bool black_patron;
      char black_title[1024];
    char game_initialFen[1024];
    /* state */
      char state_moves[1024];
      int state_wtime;
      int state_btime;
      int state_winc;
      int state_binc;
      /* status */
        int status_id;
        char status_name[1024];
      bool state_wdraw;
      bool state_bdraw;
      bool state_wtakeback;
      bool state_btakeback;
    char game_tournamentId[1024];

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if (jsoneq(json_buffer, &t[i], "game") == 0) {
      strcpy( object_scope, "game" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "status") == 0) {
      strcpy( object_scope, "status" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "id") == 0) {
      if (0 == strcmp(object_scope, "game")) {
        strncpy( game_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        game_id[len] = 0;
      } else if (0 == strcmp(object_scope, "white")) {
        strncpy( white_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        white_id[len] = 0;
      } else if (0 == strcmp(object_scope, "black")) {
        strncpy( black_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        black_id[len] = 0;
      } else if (0 == strcmp(object_scope, "status")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        status_id = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "variant") == 0) {
      strcpy( object_scope, "variant" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "key") == 0) {
      if (0 == strcmp(object_scope, "variant")) {
        strncpy( variant_key, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        variant_key[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "speed") == 0) {
      strncpy( game_speed, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      game_speed[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "rated") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      game_rated = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "createdAt") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      status_id = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "white") == 0) {
      strcpy( object_scope, "white" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "name") == 0) {
      if (0 == strcmp(object_scope, "white")) {
        strncpy( white_name, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        white_name[len] = 0;
      } else if (0 == strcmp(object_scope, "black")) {
        strncpy( black_name, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        black_name[len] = 0;
      } else if (0 == strcmp(object_scope, "status")) {
        strncpy( status_name, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        status_name[len] = 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "rating") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      if (0 == strcmp(object_scope, "white")) {
        white_rating = atoi(s);
      } else if (0 == strcmp(object_scope, "black")) {
        black_rating = atoi(s);
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "provisional") == 0) {
      if (0 == strcmp(object_scope, "white")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        white_provisional = (s[0] == 't') ? 1 : 0;
      } else if (0 == strcmp(object_scope, "black")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        black_provisional = (s[0] == 't') ? 1 : 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "online") == 0) {
      if (0 == strcmp(object_scope, "white")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        white_online = (s[0] == 't') ? 1 : 0;
      } else if (0 == strcmp(object_scope, "black")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        black_online = (s[0] == 't') ? 1 : 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "patron") == 0) {
      if (0 == strcmp(object_scope, "white")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        white_patron = (s[0] == 't') ? 1 : 0;
      } else if (0 == strcmp(object_scope, "black")) {
        strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        black_patron = (s[0] == 't') ? 1 : 0;
      }
      i++;
    } else if (jsoneq(json_buffer, &t[i], "title") == 0) {
      if (0 == strcmp(object_scope, "white")) {
        strncpy( white_title, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        white_title[len] = 0;
      } else if (0 == strcmp(object_scope, "black")) {
        strncpy( black_title, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
        black_title[len] = 0;
      }
    } else if (jsoneq(json_buffer, &t[i], "initialFen") == 0) {
      strncpy( game_speed, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      game_speed[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "state") == 0) {
      strcpy( object_scope, "state" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "moves") == 0) {
      strncpy( state_moves, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_moves[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "wtime") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_wtime = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "btime") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_btime = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "winc") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_winc = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "binc") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_binc = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "status") == 0) {
      strcpy( object_scope, "status" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "wdraw") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_wdraw = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "bdraw") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_bdraw = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "wtakeback") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_wtakeback = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "btakeback") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_btakeback = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "trounamentId") == 0) {
      strncpy( game_tournamentId, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      game_tournamentId[len] = 0;
      i++;
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
  }
}

void api_gameState( const int, const int );
void api_gameState( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  /* game_state */
    char state_moves[1024];
    int state_wtime;
    int state_btime;
    int state_winc;
    int state_binc;
    /* status */
      int status_id;
      char status_name[1024];
    bool state_wdraw;
    bool state_bdraw;
    bool state_wtakeback;
    bool state_btakeback;
  char game_id[1024];

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if (jsoneq(json_buffer, &t[i], "game_state") == 0) {
      strcpy( object_scope, "state" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "status") == 0) {
      strcpy( object_scope, "status" );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "game_id") == 0) {
      strncpy( game_id, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      game_id[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "moves") == 0) {
      strncpy( state_moves, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_moves[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "wtime") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_wtime = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "btime") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_btime = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "winc") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_winc = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "binc") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_binc = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "id") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      status_id = atoi(s);
      i++;
    } else if (jsoneq(json_buffer, &t[i], "name") == 0) {
      strncpy( state_moves, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      status_name[len] = 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "wdraw") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_wdraw = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "bdraw") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_bdraw = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "wtakeback") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_wtakeback = (s[0] == 't') ? 1 : 0;
      i++;
    } else if (jsoneq(json_buffer, &t[i], "btakeback") == 0) {
      strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      state_btakeback = (s[0] == 't') ? 1 : 0;
      i++;
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
  }
}

int lichess_api_json( int argc, char ** argv )
{
  int i, r, bytes, len;
  char command[1024];

  bytes = read_file_into_buffer( argv[1] );
  if ( bytes > 0 ) {
    r = init_parser_and_parse( bytes );
  }

  if (r < 0) {
    printf("Failed to parse JSON: %d\n", r);
    return 1;
  }

  /* Dump JSON to see if it looks right.  */
  dump(json_buffer, t, p.toknext, 0);

  /* Assume the top-level element is an object */
  if (r < 1 || t[0].type != JSMN_OBJECT) {
    printf("Object expected\n");
    return 1;
  }

  for (i = 1; i < r; i++) {
    len = t[i+1].end - t[i+1].start;
    if (jsoneq(json_buffer, &t[i], "name") == 0) {
      strncpy( command, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len );
      i++;
    } else if (jsoneq(json_buffer, &t[i], "args") == 0) {
      i++;
      i++;
      /* Put switch statement here for various messages.  */
      if (0 == strcmp(command, "connect")) {
        api_connect( i, r );
      } else if (0 == strcmp(command, "connected")) {
        api_connected( i, r );
      } else if (0 == strcmp(command, "challenge")) {
        api_challenge( i, r );
      } else if (0 == strcmp(command, "gameStart")) {
        api_gameStart( i, r );
      } else if (0 == strcmp(command, "gameState")) {
        api_gameState( i, r );
      } else {
      }
      break;
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
  }
  return EXIT_SUCCESS;
}

#ifdef  __cplusplus
}
#endif
