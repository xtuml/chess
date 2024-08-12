#include "jsmn.h"
#include "lichess_bot_sys_types.h"
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


static char** str_split(char* , const char );
static char** str_split(char* a_str, const char a_delim)
{
    char** result    = 0;
    size_t count     = 0;
    char* tmp        = a_str;
    char* last_comma = 0;
    char delim[2];
    delim[0] = a_delim;
    delim[1] = 0;

    /* Count how many elements will be extracted. */
    while (*tmp) {
        if (a_delim == *tmp) {
            count++;
            last_comma = tmp;
        }
        tmp++;
    }

    /* Add space for trailing token. */
    count += last_comma < (a_str + strlen(a_str) - 1);

    /* Add space for terminating null string so caller
       knows where the list of returned strings ends. */
    count++;

    result = malloc(sizeof(char*) * count);

    if (result) {
        size_t idx  = 0;
        char* token = strtok(a_str, delim);

        while (token) {
            *(result + idx++) = strdup(token);
            token = strtok(0, delim);
        }
        *(result + idx) = 0;
    }

    return result;
}


lichess_bot_GameSource_t encode_GameSource( char * );
lichess_bot_GameSource_t encode_GameSource( char * s )
{
  if ( 0 == Escher_strcmp( s, "LOBBY" ) ) {
    return lichess_bot_GameSource_LOBBY_e;
  } else if ( 0 == Escher_strcmp( s, "FRIEND" ) ) {
    return lichess_bot_GameSource_FRIEND_e;
  } else if ( 0 == Escher_strcmp( s, "AI" ) ) {
    return lichess_bot_GameSource_AI_e;
  } else if ( 0 == Escher_strcmp( s, "API" ) ) {
    return lichess_bot_GameSource_API_e;
  } else if ( 0 == Escher_strcmp( s, "TOURNAMENT" ) ) {
    return lichess_bot_GameSource_TOURNAMENT_e;
  } else if ( 0 == Escher_strcmp( s, "POSITION" ) ) {
    return lichess_bot_GameSource_POSITION_e;
  } else if ( 0 == Escher_strcmp( s, "IMPORT" ) ) {
    return lichess_bot_GameSource_IMPORT_e;
  } else if ( 0 == Escher_strcmp( s, "IMPORTLIVE" ) ) {
    return lichess_bot_GameSource_IMPORTLIVE_e;
  } else if ( 0 == Escher_strcmp( s, "SIMUL" ) ) {
    return lichess_bot_GameSource_SIMUL_e;
  } else if ( 0 == Escher_strcmp( s, "RELAY" ) ) {
    return lichess_bot_GameSource_RELAY_e;
  } else if ( 0 == Escher_strcmp( s, "POOL" ) ) {
    return lichess_bot_GameSource_POOL_e;
  } else if ( 0 == Escher_strcmp( s, "SWISS" ) ) {
    return lichess_bot_GameSource_SWISS_e;
  } else {
    fprintf(stderr, "Error:  unknown GameSource:  %s\n", s );
  }
  return lichess_bot_GameSource__UNINITIALIZED__e;
}


lichess_bot_Color_t encode_Color( char * );
lichess_bot_Color_t encode_Color( char * s )
{
  if ( 0 == Escher_strcmp( s, "WHITE" ) ) {
    return lichess_bot_Color_WHITE_e;
  } else if ( 0 == Escher_strcmp( s, "BLACK" ) ) {
    return lichess_bot_Color_BLACK_e;
  } else if ( 0 == Escher_strcmp( s, "RANDOM" ) ) {
    return lichess_bot_Color_RANDOM_e;
  } else {
    fprintf(stderr, "Error:  unknown Color:  %s\n", s );
  }
  return lichess_bot_Color__UNINITIALIZED__e;
}


lichess_bot_GameSpeed_t encode_GameSpeed( char * );
lichess_bot_GameSpeed_t encode_GameSpeed( char * s )
{
  if ( 0 == Escher_strcmp( s, "ULTRABULLET" ) ) {
    return lichess_bot_GameSpeed_ULTRABULLET_e;
  } else if ( 0 == Escher_strcmp( s, "BULLET" ) ) {
    return lichess_bot_GameSpeed_BULLET_e;
  } else if ( 0 == Escher_strcmp( s, "BLITZ" ) ) {
    return lichess_bot_GameSpeed_BLITZ_e;
  } else if ( 0 == Escher_strcmp( s, "RAPID" ) ) {
    return lichess_bot_GameSpeed_RAPID_e;
  } else if ( 0 == Escher_strcmp( s, "CLASSICAL" ) ) {
    return lichess_bot_GameSpeed_CLASSICAL_e;
  } else if ( 0 == Escher_strcmp( s, "CORRESPONDENCE" ) ) {
    return lichess_bot_GameSpeed_CORRESPONDENCE_e;
  } else {
    fprintf(stderr, "Error:  unknown GameSpeed:  %s\n", s );
  }
  return lichess_bot_GameSpeed__UNINITIALIZED__e;
}


lichess_bot_Variant_t encode_Variant( char * );
lichess_bot_Variant_t encode_Variant( char * s )
{
  if ( 0 == Escher_strcmp( s, "STANDARD" ) ) {
    return lichess_bot_Variant_STANDARD_e;
  } else if ( 0 == Escher_strcmp( s, "standard" ) ) {
    return lichess_bot_Variant_STANDARD_e;
  } else if ( 0 == Escher_strcmp( s, "CHESS960" ) ) {
    return lichess_bot_Variant_CHESS960_e;
  } else if ( 0 == Escher_strcmp( s, "CRAZYHOUSE" ) ) {
    return lichess_bot_Variant_CRAZYHOUSE_e;
  } else if ( 0 == Escher_strcmp( s, "ANTICHESS" ) ) {
    return lichess_bot_Variant_ANTICHESS_e;
  } else if ( 0 == Escher_strcmp( s, "ATOMIC" ) ) {
    return lichess_bot_Variant_ATOMIC_e;
  } else if ( 0 == Escher_strcmp( s, "HORDE" ) ) {
    return lichess_bot_Variant_HORDE_e;
  } else if ( 0 == Escher_strcmp( s, "KINGOFTHEHILL" ) ) {
    return lichess_bot_Variant_KINGOFTHEHILL_e;
  } else if ( 0 == Escher_strcmp( s, "RACINGKINGS" ) ) {
    return lichess_bot_Variant_RACINGKINGS_e;
  } else if ( 0 == Escher_strcmp( s, "THREECHECK" ) ) {
    return lichess_bot_Variant_THREECHECK_e;
  } else if ( 0 == Escher_strcmp( s, "FROMPOSITION" ) ) {
    return lichess_bot_Variant_FROMPOSITION_e;
  } else {
    fprintf(stderr, "Error:  unknown Variant:  %s\n", s );
  }
  return lichess_bot_Variant__UNINITIALIZED__e;
}


lichess_bot_UserTitle_t encode_UserTitle( char * );
lichess_bot_UserTitle_t encode_UserTitle( char * s )
{
  if ( 0 == Escher_strcmp( s, "GM" ) ) {
    return lichess_bot_UserTitle_GM_e;
  } else if ( 0 == Escher_strcmp( s, "WGM" ) ) {
    return lichess_bot_UserTitle_WGM_e;
  } else if ( 0 == Escher_strcmp( s, "IM" ) ) {
    return lichess_bot_UserTitle_IM_e;
  } else if ( 0 == Escher_strcmp( s, "WIM" ) ) {
    return lichess_bot_UserTitle_WIM_e;
  } else if ( 0 == Escher_strcmp( s, "FM" ) ) {
    return lichess_bot_UserTitle_FM_e;
  } else if ( 0 == Escher_strcmp( s, "WFM" ) ) {
    return lichess_bot_UserTitle_WFM_e;
  } else if ( 0 == Escher_strcmp( s, "NM" ) ) {
    return lichess_bot_UserTitle_NM_e;
  } else if ( 0 == Escher_strcmp( s, "CM" ) ) {
    return lichess_bot_UserTitle_CM_e;
  } else if ( 0 == Escher_strcmp( s, "WCM" ) ) {
    return lichess_bot_UserTitle_WCM_e;
  } else if ( 0 == Escher_strcmp( s, "WNM" ) ) {
    return lichess_bot_UserTitle_WNM_e;
  } else if ( 0 == Escher_strcmp( s, "LM" ) ) {
    return lichess_bot_UserTitle_LM_e;
  } else if ( 0 == Escher_strcmp( s, "BOT" ) ) {
    return lichess_bot_UserTitle_BOT_e;
  } else {
    fprintf(stderr, "Error:  unknown UserTitle:  %s\n", s );
  }
  return lichess_bot_UserTitle__UNINITIALIZED__e;
}


lichess_bot_ChallengeStatus_t encode_ChallengeStatus( char * );
lichess_bot_ChallengeStatus_t encode_ChallengeStatus( char * s )
{
  if ( 0 == Escher_strcmp( s, "CREATED" ) ) {
    return lichess_bot_ChallengeStatus_CREATED_e;
  } else if ( 0 == Escher_strcmp( s, "OFFLINE" ) ) {
    return lichess_bot_ChallengeStatus_OFFLINE_e;
  } else if ( 0 == Escher_strcmp( s, "CANCELED" ) ) {
    return lichess_bot_ChallengeStatus_CANCELED_e;
  } else if ( 0 == Escher_strcmp( s, "DECLINED" ) ) {
    return lichess_bot_ChallengeStatus_DECLINED_e;
  } else if ( 0 == Escher_strcmp( s, "ACCEPTED" ) ) {
    return lichess_bot_ChallengeStatus_ACCEPTED_e;
  } else {
    fprintf(stderr, "Error:  unknown ChallengeStatus:  %s\n", s );
  }
  return lichess_bot_ChallengeStatus__UNINITIALIZED__e;
}


lichess_bot_GameStatus_t encode_GameStatus( char * );
lichess_bot_GameStatus_t encode_GameStatus( char * s )
{
  if ( 0 == Escher_strcmp( s, "CREATED" ) ) {
    return lichess_bot_GameStatus_CREATED_e;
  } else if ( 0 == Escher_strcmp( s, "STARTED" ) ) {
    return lichess_bot_GameStatus_STARTED_e; 
  } else if ( 0 == Escher_strcmp( s, "ABORTED" ) ) {
    return lichess_bot_GameStatus_ABORTED_e;
  } else if ( 0 == Escher_strcmp( s, "MATE" ) ) {
    return lichess_bot_GameStatus_MATE_e;
  } else if ( 0 == Escher_strcmp( s, "RESIGN" ) ) {
    return lichess_bot_GameStatus_RESIGN_e;
  } else if ( 0 == Escher_strcmp( s, "STALEMATE" ) ) {
    return lichess_bot_GameStatus_STALEMATE_e;
  } else if ( 0 == Escher_strcmp( s, "TIMEOUT" ) ) {
    return lichess_bot_GameStatus_TIMEOUT_e;
  } else if ( 0 == Escher_strcmp( s, "DRAW" ) ) {
    return lichess_bot_GameStatus_DRAW_e;
  } else if ( 0 == Escher_strcmp( s, "OUTOFTIME" ) ) {
    return lichess_bot_GameStatus_OUTOFTIME_e;
  } else if ( 0 == Escher_strcmp( s, "CHEAT" ) ) {
    return lichess_bot_GameStatus_CHEAT_e;
  } else if ( 0 == Escher_strcmp( s, "NOSTART" ) ) {
    return lichess_bot_GameStatus_NOSTART_e;
  } else if ( 0 == Escher_strcmp( s, "UNKNOWNFINISH" ) ) {
    return lichess_bot_GameStatus_UNKNOWNFINISH_e;
  } else if ( 0 == Escher_strcmp( s, "VARIANTEND" ) ) {
    return lichess_bot_GameStatus_VARIANTEND_e;
  } else {
    fprintf(stderr, "Error:  unknown GameStatus:  %s\n", s );
  }
  return lichess_bot_GameStatus__UNINITIALIZED__e;
}


/* These macros streamline the heavy lifting being done with jsmn.  */
#define json_detect_key( KEY ) ( jsoneq(json_buffer, &t[i], KEY) == 0 )
#define json_get_string( STRVAR ) strncpy( STRVAR, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len ); STRVAR[len] = 0
#define json_get_number( NUMVAR ) strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len ); s[len] = 0; NUMVAR = atoi(s)
#define json_get_boolean( BOOLVAR ) strncpy( s, json_buffer + t[i+1].start, ( len > 1024 ) ? 1024 : len ); s[len] = 0; BOOLVAR = (s[0] == 't') ? 1 : 0


void api_connected( const int, const int );
void api_connected( const int starting_token_offset, const int token_count )
{
  Engine_chess_connected();
}

/* This one is not used but was for practice.  */
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
    if ( json_detect_key("api_base_url") ) {
      json_get_string( api_base_url );
    } else if ( json_detect_key("access_token") ) {
      json_get_string( access_token );
    } else if ( json_detect_key("max_games") ) {
      json_get_number( max_games );
    } else if ( json_detect_key("auto_open_challenge_url") ) {
      json_get_boolean( auto_open_challenge_url );
    } else if ( json_detect_key("enable_debug_logging") ) {
      json_get_boolean( enable_debug_logging );
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
    i++;
  }
}

void api_challenge( const int, const int );
void api_challenge( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  lichess_bot_sdt_Challenge challenge;

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if ( json_detect_key("challenge") ) {
      strcpy( object_scope, "challenge" );
    } else if ( json_detect_key("id") ) {
      if (0 == strcmp(object_scope, "challenge")) {
        json_get_string( challenge.id );
      } else if (0 == strcmp(object_scope, "challenger")) {
        json_get_string( challenge.challenger.id );
      } else if (0 == strcmp(object_scope, "destUser")) {
        json_get_string( challenge.destUser.id );
      }
    } else if ( json_detect_key("url") ) {
      json_get_string( challenge.url );
    } else if ( json_detect_key("status") ) {
      json_get_string( s );
      challenge.status = encode_ChallengeStatus( s );
    } else if ( json_detect_key("challenger") ) {
      strcpy( object_scope, "challenger" );
    } else if ( json_detect_key("destUser") ) {
      strcpy( object_scope, "destUser" );
    } else if ( json_detect_key("name") ) {
      if (0 == strcmp(object_scope, "challenger")) {
        json_get_string( challenge.challenger.name );
      } else if (0 == strcmp(object_scope, "destUser")) {
        json_get_string( challenge.destUser.name );
      }
    } else if ( json_detect_key("rating") ) {
      if (0 == strcmp(object_scope, "challenger")) {
        json_get_number( challenge.challenger.rating );
      } else if (0 == strcmp(object_scope, "destUser")) {
        json_get_number( challenge.destUser.rating );
      }
    } else if ( json_detect_key("provisional") ) {
      if (0 == strcmp(object_scope, "challenger")) {
        json_get_boolean( challenge.challenger.provisional );
      } else if (0 == strcmp(object_scope, "destUser")) {
        json_get_boolean( challenge.destUser.provisional );
      }
    } else if ( json_detect_key("online") ) {
      if (0 == strcmp(object_scope, "challenger")) {
        json_get_boolean( challenge.challenger.online );
      } else if (0 == strcmp(object_scope, "destUser")) {
        json_get_boolean( challenge.destUser.online );
      }
    } else if ( json_detect_key("patron") ) {
      if (0 == strcmp(object_scope, "challenger")) {
        json_get_boolean( challenge.challenger.patron );
      } else if (0 == strcmp(object_scope, "destUser")) {
        json_get_boolean( challenge.destUser.patron );
      }
    } else if ( json_detect_key("title") ) {
      json_get_string( s );
      if (0 == strcmp(object_scope, "challenger")) {
        challenge.challenger.title = encode_UserTitle( s );
      } else if (0 == strcmp(object_scope, "destUser")) {
        challenge.destUser.title = encode_UserTitle( s );
      }
    } else if ( json_detect_key("variant") ) {
      strcpy( object_scope, "variant" );
    } else if ( json_detect_key("key") ) {
      if (0 == strcmp(object_scope, "variant")) {
        json_get_string( s );
        challenge.variant = encode_Variant( s );
      }
    } else if ( json_detect_key("rated") ) {
      json_get_boolean( challenge.rated );
    } else if ( json_detect_key("speed") ) {
      json_get_string( s );
      challenge.speed = encode_GameSpeed( s );
    } else if ( json_detect_key("timeControl") ) {
      strcpy( object_scope, "timeControl" );
    } else if ( json_detect_key("type") ) {
      if (0 == strcmp(object_scope, "timeControl")) {
        json_get_string( challenge.timeControl.controlType );
      }
    } else if ( json_detect_key("limit") ) {
      if (0 == strcmp(object_scope, "timeControl")) {
        json_get_number( challenge.timeControl.limit );
      }
    } else if ( json_detect_key("increment") ) {
      if (0 == strcmp(object_scope, "timeControl")) {
        json_get_number( challenge.timeControl.increment );
      }
    } else if ( json_detect_key("daysPerTurn") ) {
      if (0 == strcmp(object_scope, "timeControl")) {
        json_get_number( challenge.timeControl.daysPerTurn );
      }
    } else if ( json_detect_key("show") ) {
      if (0 == strcmp(object_scope, "timeControl")) {
        json_get_string( challenge.timeControl.show );
      }
    } else if ( json_detect_key("color") ) {
      json_get_string( s );
      challenge.color = encode_Color( s );
    } else if ( json_detect_key("initialFen") ) {
      json_get_string( challenge.initialFen );
    } else if ( json_detect_key("declineReason") ) {
      json_get_string( challenge.declineReason );
    } else if ( json_detect_key("declineReasonKey") ) {
      json_get_string( challenge.declineReasonKey );
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
    i++;
  }
  Engine_chess_challenge( challenge );
}

void api_gameStart( const int, const int );
void api_gameStart( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  lichess_bot_sdt_GameEventInfo game_event;

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if ( json_detect_key("game_event") ) {
      strcpy( object_scope, "game_event" );
    } else if ( json_detect_key("status") ) {
      strcpy( object_scope, "status" );
    } else if ( json_detect_key("id") ) {
      if (0 == strcmp(object_scope, "game_event")) {
        json_get_string( game_event.id );
      } else if (0 == strcmp(object_scope, "status")) {
        /* skip */
      }
    } else if ( json_detect_key("source") ) {
      if (0 == strcmp(object_scope, "game_event")) {
        json_get_string( s );
        game_event.source = encode_GameSource( s );
      }
    } else if ( json_detect_key("name") ) {
      if (0 == strcmp(object_scope, "status")) {
        json_get_string( s );
        game_event.status = encode_GameStatus( s );
      }
    } else if ( json_detect_key("winner") ) {
      json_get_string( s );
      game_event.winner = encode_Color( s );
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
    i++;
  }
  Engine_chess_gameStart( game_event );
}

void api_gameFull( const int, const int );
void api_gameFull( const int starting_token_offset, const int token_count )
{
  int i, len;
  char ** tokens;
  char s[1024];
  char object_scope[1024] = {0};
  lichess_bot_sdt_Game game;

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if ( json_detect_key("game") ) {
      strcpy( object_scope, "game" );
    } else if ( json_detect_key("status") ) {
      strcpy( object_scope, "status" );
    } else if ( json_detect_key("white") ) {
      strcpy( object_scope, "white" );
    } else if ( json_detect_key("black") ) {
      strcpy( object_scope, "black" );
    } else if ( json_detect_key("id") ) {
      if (0 == strcmp(object_scope, "game")) {
        json_get_string( game.id );
      } else if (0 == strcmp(object_scope, "white")) {
        json_get_string( game.white.id );
      } else if (0 == strcmp(object_scope, "black")) {
        json_get_string( game.black.id );
      } else if (0 == strcmp(object_scope, "status")) {
        /* skip */
      }
    } else if ( json_detect_key("variant") ) {
      strcpy( object_scope, "variant" );
    } else if ( json_detect_key("key") ) {
      if (0 == strcmp(object_scope, "variant")) {
        json_get_string( s );
        game.variant = encode_Variant( s );
      }
    } else if ( json_detect_key("speed") ) {
      json_get_string( s );
      game.speed = encode_GameSpeed( s );
    } else if ( json_detect_key("rated") ) {
      json_get_boolean( game.rated );
    } else if ( json_detect_key("createdAt") ) {
      json_get_number( game.createdAt );
    } else if ( json_detect_key("white") ) {
      strcpy( object_scope, "white" );
    } else if ( json_detect_key("name") ) {
      if (0 == strcmp(object_scope, "white")) {
        json_get_string( game.white.name );
      } else if (0 == strcmp(object_scope, "black")) {
        json_get_string( game.black.name );
      } else if (0 == strcmp(object_scope, "status")) {
        json_get_string( s );
        game.gameState.status = encode_GameStatus( s );
      }
    } else if ( json_detect_key("rating") ) {
      if (0 == strcmp(object_scope, "white")) {
        json_get_number( game.white.rating );
      } else if (0 == strcmp(object_scope, "black")) {
        json_get_number( game.black.rating );
      }
    } else if ( json_detect_key("provisional") ) {
      if (0 == strcmp(object_scope, "white")) {
        json_get_boolean( game.white.provisional );
      } else if (0 == strcmp(object_scope, "black")) {
        json_get_boolean( game.black.provisional );
      }
    } else if ( json_detect_key("online") ) {
      if (0 == strcmp(object_scope, "white")) {
        json_get_boolean( game.white.online );
      } else if (0 == strcmp(object_scope, "black")) {
        json_get_boolean( game.black.online );
      }
    } else if ( json_detect_key("patron") ) {
      if (0 == strcmp(object_scope, "white")) {
        json_get_boolean( game.white.patron );
      } else if (0 == strcmp(object_scope, "black")) {
        json_get_boolean( game.black.patron );
      }
    } else if ( json_detect_key("title") ) {
      json_get_string( s );
      if (0 == strcmp(object_scope, "white")) {
        game.white.title = encode_UserTitle( s );
      } else if (0 == strcmp(object_scope, "black")) {
        game.black.title = encode_UserTitle( s );
      }
    } else if ( json_detect_key("initialFen") ) {
      json_get_string( game.initialFen );
    } else if ( json_detect_key("state") ) {
      strcpy( object_scope, "state" );
    } else if ( json_detect_key("moves") ) {
      json_get_string( s );
      tokens = str_split(s, ' ');
      if (tokens) {
        int j;
        for (j = 0; *(tokens + j); j++) {
            Escher_strcpy( game.gameState.moves[j], *(tokens + j) );
            free(*(tokens + j));
        }
        free(tokens);
        game.gameState.move_count = j; // We maintain move_count in game_state.
        fprintf(stderr,"decoding GameFull, move_count is %d\n", game.gameState.move_count );
      }
    } else if ( json_detect_key("wtime") ) {
      json_get_number( game.gameState.wtime );
    } else if ( json_detect_key("btime") ) {
      json_get_number( game.gameState.btime );
    } else if ( json_detect_key("winc") ) {
      json_get_number( game.gameState.winc );
    } else if ( json_detect_key("binc") ) {
      json_get_number( game.gameState.binc );
    } else if ( json_detect_key("id") ) {
      /* skip */
    } else if ( json_detect_key("name") ) {
      json_get_string( s );
      game.gameState.status = encode_GameStatus( s );
    } else if ( json_detect_key("wdraw") ) {
      json_get_boolean( game.gameState.wdraw );
    } else if ( json_detect_key("bdraw") ) {
      json_get_boolean( game.gameState.bdraw );
    } else if ( json_detect_key("wtakeback") ) {
      json_get_boolean( game.gameState.wtakeback );
    } else if ( json_detect_key("btakeback") ) {
      json_get_boolean( game.gameState.btakeback );
    } else if ( json_detect_key("tournamentId") ) {
      json_get_string( game.tournamentId );
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
    i++;
  }
  Engine_chess_gameFull( game );
}

void api_gameState( const int, const int );
void api_gameState( const int starting_token_offset, const int token_count )
{
  int i, len;
  char ** tokens;
  char s[1024];
  char object_scope[1024] = {0};
  char game_id[1024];
  lichess_bot_sdt_GameState game_state;
  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if ( json_detect_key("game_state") ) {
      strcpy( object_scope, "state" );
    } else if ( json_detect_key("status") ) {
      strcpy( object_scope, "status" );
    } else if ( json_detect_key("game_id") ) {
      json_get_string( game_id );
    } else if ( json_detect_key("moves") ) {
      json_get_string( s );
      tokens = str_split(s, ' ');
      if (tokens) {
        int j;
        for (j = 0; *(tokens + j); j++) {
          Escher_strcpy( game_state.moves[j], *(tokens + j) );
          free(*(tokens + j));
        }
        free(tokens);
        game_state.move_count = j; // We maintain move_count in game_state.
        fprintf(stderr,"decoding GameState, move_count is %d\n", game_state.move_count );
      }
    } else if ( json_detect_key("wtime") ) {
      json_get_number( game_state.wtime );
    } else if ( json_detect_key("btime") ) {
      json_get_number( game_state.btime );
    } else if ( json_detect_key("winc") ) {
      json_get_number( game_state.winc );
    } else if ( json_detect_key("binc") ) {
      json_get_number( game_state.binc );
    } else if ( json_detect_key("id") ) {
      /* skip */
    } else if ( json_detect_key("name") ) {
      json_get_string( s );
      game_state.status = encode_GameStatus( s );
    } else if ( json_detect_key("winner") ) {
      json_get_string( s );
      game_state.winner = encode_Color( s );
    } else if ( json_detect_key("wdraw") ) {
      json_get_boolean( game_state.wdraw );
    } else if ( json_detect_key("bdraw") ) {
      json_get_boolean( game_state.bdraw );
    } else if ( json_detect_key("wtakeback") ) {
      json_get_boolean( game_state.wtakeback );
    } else if ( json_detect_key("btakeback") ) {
      json_get_boolean( game_state.btakeback );
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
    i++;
  }
  Engine_chess_gameState( game_id, game_state );
}

void api_gameFinish( const int, const int );
void api_gameFinish( const int starting_token_offset, const int token_count )
{
  int i, len;
  char s[1024];
  char object_scope[1024] = {0};
  lichess_bot_sdt_GameEventInfo game_event;

  for (i = starting_token_offset; i < token_count; i++) {
    len = t[i+1].end - t[i+1].start;
    if ( json_detect_key("game_event") ) {
      strcpy( object_scope, "game_event" );
    } else if ( json_detect_key("status") ) {
      strcpy( object_scope, "status" );
    } else if ( json_detect_key("id") ) {
      if (0 == strcmp(object_scope, "game_event")) {
        json_get_string( game_event.id );
      } else if (0 == strcmp(object_scope, "status")) {
        /* skip */
      }
    } else if ( json_detect_key("source") ) {
      if (0 == strcmp(object_scope, "game_event")) {
        json_get_string( s );
        game_event.source = encode_GameSource( s );
      }
    } else if ( json_detect_key("name") ) {
      if (0 == strcmp(object_scope, "status")) {
        json_get_string( s );
        game_event.status = encode_GameStatus( s );
      }
    } else if ( json_detect_key("winner") ) {
      json_get_string( s );
      game_event.winner = encode_Color( s );
    } else {
      printf("Unexpected key: %.*s\n", t[i].end - t[i].start, json_buffer + t[i].start);
    }
    i++;
  }
  Engine_chess_gameFinish( game_event );
}

int lichess_api_json( char * filename )
{
  int i, r, bytes, len;
  char command[1024];

  bytes = read_file_into_buffer( filename );
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
    if ( json_detect_key("name") ) {
      json_get_string( command );
      i++;
    } else if ( json_detect_key("args") ) {
      i++;
      i++;
      /* Put switch statement here for various messages.  */
      if (0 == strcmp(command, "connect")) {
        api_connect( i, r );
      } else if (0 == strcmp(command, "connected")) {
        api_connected( i, r );
      } else if (0 == strcmp(command, "challenge")) {
        api_challenge( i, r );
      } else if (0 == strcmp(command, "gameFull")) {
        api_gameFull( i, r );
      } else if (0 == strcmp(command, "gameStart")) {
        api_gameStart( i, r );
      } else if (0 == strcmp(command, "gameState")) {
        api_gameState( i, r );
      } else if (0 == strcmp(command, "gameFinish")) {
        api_gameFinish( i, r );
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
