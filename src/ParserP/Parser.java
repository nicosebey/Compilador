//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramaticaActualizada.y"
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
import AnalizadorLexico.TablaSimbolos;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import CodigoIntermedio.*;
//#line 26 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short ID=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short DO=269;
public final static short FUN=270;
public final static short DOUBLE=271;
public final static short USLINTEGER=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short ASIGNACION=275;
public final static short CASE=276;
public final static short VOID=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    4,    4,    2,    2,
    2,    7,    7,    5,    5,    9,    9,    9,   11,   14,
   17,   10,   18,   19,   10,   10,   12,   16,   16,   15,
   15,   20,   13,   21,   22,   13,   23,   13,   24,   13,
   13,   25,    8,   26,    8,    6,    6,    6,    6,   29,
   29,   31,   31,   31,   27,   27,   34,   34,   34,   34,
   32,   32,   32,   33,   33,   37,   35,   35,   39,   38,
   36,   36,   36,   40,   40,   40,   40,   28,   42,   28,
   28,   28,   44,   30,   30,   45,   45,   47,   43,   43,
   43,   46,   46,   46,   48,   48,   41,   49,   49,   49,
   49,   49,   49,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    1,    2,    3,    3,    2,    2,
    1,    2,    2,    2,    1,    3,    1,    1,    2,    0,
    0,    9,    0,    0,    8,    6,    5,    2,    1,    3,
    1,    0,    8,    0,    0,    8,    0,    6,    0,    4,
    3,    0,    2,    0,    2,    1,    1,    1,    1,    4,
    1,    4,    4,    2,    4,    1,    4,    4,    4,    3,
    4,    3,    3,    2,    1,    0,    5,    1,    0,    5,
    1,    1,    2,    4,    4,    4,    3,    3,    0,    6,
    2,    3,    0,    4,    1,    2,    2,    0,    4,    3,
    1,    3,    3,    1,    1,    1,    3,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   44,   42,    0,    0,    0,    0,
    0,    1,    2,    4,    0,    0,   11,    0,   15,    0,
   46,   47,   48,   49,   51,    0,   56,    0,   85,    5,
    0,   71,   72,   96,    0,    0,   95,    0,    0,   94,
    0,    0,    0,    0,    0,   45,   43,    0,   54,    0,
    0,    0,    0,    0,   12,    9,   13,   10,   17,    0,
   18,    0,    0,    0,    0,   65,    0,   68,    0,    0,
    7,    6,    0,   73,    0,    0,    0,    0,    0,    0,
   77,  101,  102,  103,   98,   99,  100,    0,    0,    0,
    0,   62,    0,    0,    8,    3,   19,    0,   23,    0,
   20,    0,    0,   60,   64,    0,    0,   82,   79,   78,
   84,    0,    0,   92,   93,   75,   76,   74,    0,    0,
   52,   53,   50,   59,   61,   16,    0,    0,    0,   57,
   58,   55,   69,   66,    0,   89,   27,    0,    0,    0,
    0,    0,    0,    0,    0,   26,    0,    0,   24,    0,
   40,    0,   70,   67,   80,    0,    0,    0,    0,    0,
    0,   30,    0,   25,    0,    0,   38,   21,   28,    0,
   35,   22,   33,   36,
};
final static short yydgoto[] = {                         11,
   12,   30,   31,   14,   15,   16,   17,   18,   60,   19,
   61,   20,  101,  129,  149,  164,  172,  127,  157,  158,
  159,  174,  160,  141,   47,   46,   21,   22,   23,   24,
   25,   26,   65,   27,   66,   37,  144,   68,  143,   28,
   43,  135,   44,  111,   29,   39,  136,   40,   88,
};
final static short yysindex[] = {                      -102,
 -131,  -42,  -33, -244,    0,    0,  -27,  -21,  -39, -131,
    0,    0,    0,    0,    8,   30,    0, -241,    0, -131,
    0,    0,    0,    0,    0,    6,    0,   41,    0,    0,
   88,    0,    0,    0,  -21, -236,    0,  -15,  -28,    0,
  -21,  -21, -229,    1,   -5,    0,    0, -207,    0, -198,
  -15,  -23, -194,   75,    0,    0,    0,    0,    0,  -57,
    0,  136,   24,   24,   21,    0,  -54,    0, -131, -180,
    0,    0,  -15,    0,  -21,  -21,  -21,  -21,   45,  -30,
    0,    0,    0,    0,    0,    0,    0,  -21,   50,   56,
  -26,    0,   27,   58,    0,    0,    0, -169,    0, -166,
    0,  -19,   12,    0,    0, -168, -165,    0,    0,    0,
    0,  -28,  -28,    0,    0,    0,    0,    0,  -15,  -78,
    0,    0,    0,    0,    0,    0,   63,   70, -162,    0,
    0,    0,    0,    0, -102,    0,    0,  150,  163,   64,
 -144,   73, -102, -102, -154,    0,  -40, -131,    0,    0,
    0,  163,    0,    0,    0,   76,   77,    2, -131, -137,
   77,    0,    3,    0, -131,  109,    0,    0,    0,  123,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   33,  -12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    0,    0,   35,
    0,    0,    0,    0,    0,    0,    0,    0,   36,    0,
    0,    0,   37,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   19,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -7,   -2,    0,    0,    0,    0,    0,  -24,    0,
    0,    0,    0,    0,    0,    0,    0, -130,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -146,
    0,    0,    0,    0,    0,    0,    0,   89,    0,   54,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   38,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -20,   40,   -4,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -16,  -29,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   44,    0,  -18,   57,    0,    0,    0,    0,
  -32,    0,   65,    0,    0,   14,    0,   15,    0,
};
final static int YYTABLESIZE=439;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        156,
   53,   98,   36,  107,   36,   54,   42,   70,   79,   80,
  118,   36,   50,   77,  123,   62,   97,   92,   78,   45,
   10,   36,   59,   36,   74,   36,   81,   76,   91,   75,
   91,   91,   91,   90,   89,   90,   90,   90,   88,   13,
   88,   88,   88,   76,  137,   75,  105,   91,   91,   91,
   36,   56,   90,   90,   90,   90,   36,   88,   88,   88,
   85,   87,   86,   63,   91,   36,   38,   13,   36,   94,
   72,   36,   51,   58,  105,  108,   86,   87,   14,   81,
   83,   29,   67,  105,  105,  116,  109,  110,  112,  113,
  120,  114,  115,   72,  126,   93,  121,  128,  125,   73,
  133,   72,  139,  134,  150,  130,  102,  103,   67,  140,
  142,  151,  152,  155,  145,  138,  162,  163,  167,   67,
   67,   67,  153,  154,  165,   39,   41,  169,   64,   31,
    0,  168,    2,    3,  148,  161,  132,    0,    4,    5,
    6,   63,    7,    8,    9,    0,    0,  148,    0,   67,
    0,    0,  119,    1,  166,    0,    0,    0,   67,   67,
  170,    2,    3,   10,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,   13,    0,   32,   72,    0,    0,
    0,    0,   13,   13,    0,    2,    3,   72,    0,    0,
    0,    4,    5,    6,    0,    7,    8,    9,    0,   96,
    0,  106,    0,    0,    0,   72,   97,    0,    0,   72,
    0,   38,   71,    0,    0,    0,    0,   32,   33,   32,
   33,   34,   41,   34,   52,  117,   32,   33,   48,  122,
   34,   97,   35,  171,   35,   49,   32,   33,   32,   33,
   32,   33,   34,   91,   91,   91,   91,  173,   90,   90,
   90,   90,    0,   88,   88,   88,   88,   82,   83,   84,
    0,   63,    0,   55,    0,   32,   33,  131,    0,    0,
    0,   32,   33,    0,   63,    0,  104,    0,   63,   63,
   32,   33,  124,   32,   33,   57,   32,   33,   86,   87,
   14,   81,   83,   29,    0,    0,   69,    0,    0,    0,
    0,    0,    0,    0,    2,    3,    0,    0,    0,   37,
    4,    5,    6,    0,    7,    8,    9,   34,   34,    0,
    0,    0,    0,   34,   34,   34,    0,   34,   34,   34,
   95,    0,    0,    0,    0,    0,    0,    0,    2,    3,
    0,    0,    0,    0,    4,    5,    6,    0,    7,    8,
    9,    2,    3,    0,    0,    0,    0,    4,    5,    6,
    0,    7,    8,    9,    0,    0,    0,    0,    0,    0,
    0,    0,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,    0,    2,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,    2,
    3,    0,    0,    0,    0,    4,    5,    6,   99,    7,
    8,    9,  100,    2,    3,    0,    0,    0,    0,    4,
    5,    6,  146,    7,    8,    9,  147,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   59,   45,   58,   45,   10,   40,   28,   41,   42,
   41,   45,   40,   42,   41,   20,   41,   41,   47,  264,
  123,   45,  264,   45,  261,   45,  256,   43,   41,   45,
   43,   44,   45,   41,   40,   43,   44,   45,   41,    0,
   43,   44,   45,   43,  123,   45,   65,   60,   61,   62,
   45,   44,   60,   61,   62,  263,   45,   60,   61,   62,
   60,   61,   62,   45,  263,   45,    2,   28,   45,  264,
   31,   45,    8,   44,   93,  256,   44,   44,   44,   44,
   44,   44,   26,  102,  103,   41,  267,  268,   75,   76,
   41,   77,   78,   54,  264,   52,   41,  264,   41,   35,
  269,   62,   40,  269,   41,  125,   63,   64,   52,   40,
  273,  256,   40,  268,  135,  120,   41,   41,  256,   63,
   64,   65,  143,  144,  123,  256,  273,  125,  123,   41,
   -1,  161,  264,  265,  139,  152,  125,   -1,  270,  271,
  272,  123,  274,  275,  276,   -1,   -1,  152,   -1,   93,
   -1,   -1,   88,  256,  159,   -1,   -1,   -1,  102,  103,
  165,  264,  265,  123,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,  135,   -1,  123,  138,   -1,   -1,
   -1,   -1,  143,  144,   -1,  264,  265,  148,   -1,   -1,
   -1,  270,  271,  272,   -1,  274,  275,  276,   -1,  125,
   -1,  256,   -1,   -1,   -1,  166,  264,   -1,   -1,  170,
   -1,  147,  125,   -1,   -1,   -1,   -1,  260,  261,  260,
  261,  264,  256,  264,  264,  256,  260,  261,  256,  256,
  264,  256,  275,  125,  275,  263,  260,  261,  260,  261,
  260,  261,  264,  256,  257,  258,  259,  125,  256,  257,
  258,  259,   -1,  256,  257,  258,  259,  257,  258,  259,
   -1,  256,   -1,  256,   -1,  260,  261,  256,   -1,   -1,
   -1,  260,  261,   -1,  256,   -1,  256,   -1,  260,  261,
  260,  261,  256,  260,  261,  256,  260,  261,  256,  256,
  256,  256,  256,  256,   -1,   -1,  256,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  264,  265,   -1,   -1,   -1,  256,
  270,  271,  272,   -1,  274,  275,  276,  264,  265,   -1,
   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,
  276,  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,
   -1,  274,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,   -1,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,  264,
  265,   -1,   -1,   -1,   -1,  270,  271,  272,  273,  274,
  275,  276,  277,  264,  265,   -1,   -1,   -1,   -1,  270,
  271,  272,  273,  274,  275,  276,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
};
}
final static short YYFINAL=11;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"S_MAYOR_IGUAL","S_MENOR_IGUAL","S_DISTINTO",
"CTE_D","CTE_USLINTEGER","COMENTARIO","CADENA","ID","IF","THEN","ELSE","END_IF",
"DO","FUN","DOUBLE","USLINTEGER","RETURN","PRINT","ASIGNACION","CASE","VOID",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : sentencia",
"bloque : '{' bloque_sentencias '}'",
"bloque : error_bloque",
"bloque_sentencias : sentencia",
"bloque_sentencias : bloque_sentencias sentencia",
"error_bloque : error bloque_sentencias '}'",
"error_bloque : '{' bloque_sentencias error",
"sentencia : declaracion ','",
"sentencia : ejecucion ','",
"sentencia : error_sentencia_d",
"error_sentencia_d : declaracion error",
"error_sentencia_d : ejecucion error",
"declaracion : tipo lista_de_variables",
"declaracion : funcion",
"lista_de_variables : lista_de_variables ';' ID",
"lista_de_variables : ID",
"lista_de_variables : error_lista_de_variables",
"error_lista_de_variables : lista_de_variables ID",
"$$1 :",
"$$2 :",
"funcion : encabezado_fun bloque_sentencias clousure $$1 RETURN '(' retorno_funcion fin_fun $$2",
"$$3 :",
"$$4 :",
"funcion : encabezado_fun bloque_sentencias RETURN $$3 '(' retorno_funcion $$4 fin_fun",
"funcion : FUN ID '(' ')' bloque_sentencias RETURN",
"encabezado_fun : FUN ID '(' ')' '{'",
"fin_fun : ')' '}'",
"fin_fun : ')'",
"retorno_funcion : ID '(' ')'",
"retorno_funcion : bloque_sentencias",
"$$5 :",
"clousure : VOID ID '(' ')' $$5 '{' bloque_sentencias '}'",
"$$6 :",
"$$7 :",
"clousure : VOID ID '(' ')' $$6 bloque_sentencias '}' $$7",
"$$8 :",
"clousure : VOID ID '(' ')' $$8 error",
"$$9 :",
"clousure : VOID ID $$9 error",
"clousure : VOID ID '('",
"$$10 :",
"tipo : USLINTEGER $$10",
"$$11 :",
"tipo : DOUBLE $$11",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')'",
"print : error_print",
"error_print : PRINT error CADENA ')'",
"error_print : PRINT '(' CADENA error",
"error_print : PRINT CADENA",
"control : case_encabezado '{' lista_acciones '}'",
"control : error_control",
"error_control : case_encabezado error lista_acciones '}'",
"error_control : case_encabezado '{' lista_acciones error",
"error_control : CASE ID lista_acciones error",
"error_control : case_encabezado lista_acciones error",
"case_encabezado : CASE '(' ID ')'",
"case_encabezado : CASE ID ')'",
"case_encabezado : CASE '(' ID",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"$$12 :",
"accion : cte ':' DO $$12 bloque",
"accion : error_accion",
"$$13 :",
"error_accion : cte error DO $$13 bloque",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"cte : '-' CTE_USLINTEGER",
"if_condicion : IF '(' condicion ')'",
"if_condicion : IF error condicion ')'",
"if_condicion : IF '(' condicion error",
"if_condicion : IF condicion error",
"seleccion : if_condicion bloque END_IF",
"$$14 :",
"seleccion : if_condicion bloque ELSE $$14 bloque END_IF",
"seleccion : if_condicion error",
"seleccion : if_condicion bloque error",
"$$15 :",
"asignacion : ID ASIGNACION expresion $$15",
"asignacion : error_asignacion",
"error_asignacion : ID expresion",
"error_asignacion : ASIGNACION expresion",
"$$16 :",
"expresion : expresion '+' termino $$16",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : cte",
"factor : ID",
"condicion : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//#line 690 "gramaticaActualizada.y"





private ArchController lexico;
private TablaSimbolos tSimbolos;
private CtrlTercetos ctrlTercetos;
private ArrayList<String> ambito;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
  this.tSimbolos = lexico.getTS();
  this.ctrlTercetos = new CtrlTercetos();
  ambito = new ArrayList<String>();
  ambito.add("main");
} 

public int yylex(){
    Token token = this.lexico.getToken();
   if(token != null ){ 
    int val =token.getTipo();
    yylval = new ParserVal(token);
    return val;
}
   else{
        for (Terceto t : ctrlTercetos.getTercetos())
                t.getNro();
        return 0;
        }
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}

public CtrlTercetos getCtrlTercetos(){
    return ctrlTercetos;
}
//#line 488 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 41 "gramaticaActualizada.y"
{System.out.println("se cargo una sentencia");}
break;
case 7:
//#line 53 "gramaticaActualizada.y"
{ lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" Error sintactico: falta  llave ");}
break;
case 8:
//#line 54 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" Error sintactico: falta llave ");}
break;
case 9:
//#line 58 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se cargo una lista de variables");}
break;
case 12:
//#line 66 "gramaticaActualizada.y"
{ lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea()+"Error sintactico: falta la coma");}
break;
case 13:
//#line 67 "gramaticaActualizada.y"
{	lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea()+"Error sintactico: falta la coma");}
break;
case 14:
//#line 72 "gramaticaActualizada.y"
{   String tipo = ((Token) val_peek(1).obj).getId();
                                               /* System.out.println("el tipo de las variables es "+tipo);*/
                                                for(Token t : (ArrayList<Token>)val_peek(0).obj){
                                                   
                                                   t.setTipoReal(tipo);
                                                   t.setAmbito(ambito.get(ambito.size()-1));
                                                   t.setVisible(true);

                                                   if(!lexico.getTS().fueDeclarada(t.getId())){
                                                      lexico.getTS().setDeclaracion(t.getId(),t);
                                                      System.out.println("se agrego el id "+t.getId()+"del tipo "+tipo);
                                                     }
                                                    else
                                                      System.out.println("el id ya existe");
                                                   }
                                                System.out.println("imprimo las variables de la tablaaaaaaaaaaaaaaaaaaa");   
                                                Hashtable<String, Token> ts = lexico.getTS().getDeclaradas();
                                                Enumeration e = ts.keys();
                                                String clave;
                                                while( e.hasMoreElements() ){
                                                clave = (String) e.nextElement();
                                                System.out.println( "Clave : " + clave );
                                                System.out.println(ts.get(clave).getId()+" "+ts.get(clave).getTipo()+" : TIPO"+ts.get(clave).getTipoReal()+" : TIPOreal"); 
                                                }
                                           }
break;
case 16:
//#line 101 "gramaticaActualizada.y"
{ ArrayList<Token> lista  = (ArrayList<Token>) val_peek(2).obj;
                                                 /* System.out.println(((Token) $3.obj).getId()+" SE AGREGO A LA LISTA DE VARIABLES");*/
                                                  lista.add((Token)val_peek(0).obj);
                                                  yyval = new ParserVal(lista);   
                                                }
break;
case 17:
//#line 106 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea() +" se agrego el identificador"+ID);
                           /* System.out.println(((Token) $1.obj).getId()+" SE AGREGO A LA LISTA DE VARIABLES ");*/
                            ArrayList<Token> lista = new ArrayList<>();
                            lista.add((Token)val_peek(0).obj);
                            yyval = new ParserVal(lista);
                            }
break;
case 18:
//#line 112 "gramaticaActualizada.y"
{/*ArrayList<Token> lista = new ArrayList<>();
                                         lista.add((Token)$1.obj);
                                         $$ = new ParserVal(lista);
                                         //MENSAJE ERROR */}
break;
case 19:
//#line 118 "gramaticaActualizada.y"
{lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ; que separa las variables");}
break;
case 20:
//#line 121 "gramaticaActualizada.y"
{Terceto terceto2 = new Terceto("Corte_cl",null,null);/*ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL BLOQUE DE LA FUNCION*/
                                                ctrlTercetos.agregarTerceto(terceto2);
                                                }
break;
case 21:
//#line 123 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un clousure FUN");}
break;
case 22:
//#line 124 "gramaticaActualizada.y"
{
                                                 /*Terceto terceto = new Terceto ($3.sval,,null);*/
                                                }
break;
case 23:
//#line 129 "gramaticaActualizada.y"
{ String nombreFun = ctrlTercetos.getFuncionActual();
                                                   Terceto terceto2 = new Terceto("Corte_Fun",null,null);/*ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL BLOQUE DE LA FUNCION*/
                                                   ctrlTercetos.agregarTerceto(terceto2);
                                                   if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                        Token funcion = new Token(nombreFun);
                                                        lexico.getTS().setDeclaracion(nombreFun,funcion);
                                                    
                                                    }
                                                    }
break;
case 24:
//#line 138 "gramaticaActualizada.y"
{        
                                                Terceto terceto3 = new Terceto ("Retorno_func",(Token) val_peek(2).obj,new Token("-"));
                                                ctrlTercetos.agregarTerceto(terceto3);
                                                Terceto terceto4 = new Terceto ("corte_retorno",new Token("-"),new Token("-"));/*ESTE TERCETO LO CREO PARA SABER CUANDO TERMINA EL RETORNO DE LA FUNCION*/
                                                ctrlTercetos.agregarTerceto(terceto4);
                                                lexico.getTS().setInvisibles(ctrlTercetos.getFuncionActual());
                                                ambito.remove(ambito.size()-1);

                                        }
break;
case 25:
//#line 146 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego un FUN");

                                                 ctrlTercetos.setFuncionActual("main");}
break;
case 26:
//#line 150 "gramaticaActualizada.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");
                                /* Terceto terceto = new Terceto ($3.sval,,null);*/
                                   /*MENSAJE ERROR */
                                }
break;
case 27:
//#line 161 "gramaticaActualizada.y"
{                    
                                                ctrlTercetos.setFuncionActual(((Token)val_peek(3).obj).getId());
                                                String nombreF = ((Token)val_peek(3).obj).getId();    
                                                ambito.add(nombreF);
                                                String nombreFun = ((Token)val_peek(3).obj).getId();
                                                Token funcion = new Token (nombreF);
                                                Terceto terceto = new Terceto ("FUNCION",funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                
                                                /*TENGO QUE CONTROLAR QUE LA FUNCION NO SE HAYA DECLARADO ANTES, QUE LAS VARIABLES ESTEN DECLARADAS EN LA FUN*/
                }
break;
case 29:
//#line 175 "gramaticaActualizada.y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+" en la linea "+lexico.getFuente().getLinea() +" falta alguna llave");
                   Terceto terceto = new Terceto("corte_fun",new Token("-"),new Token("-"));}
break;
case 31:
//#line 180 "gramaticaActualizada.y"
{System.out.println("55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");}
break;
case 32:
//#line 183 "gramaticaActualizada.y"
{    
                                                String nombreFun = ((Token)val_peek(2).obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                
                            }
break;
case 33:
//#line 191 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun = ((Token)val_peek(6).obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                    Token funcion = new Token(nombreFun);
                                                    lexico.getTS().setDeclaracion(nombreFun,funcion);
                                                    }
                                          }
break;
case 34:
//#line 198 "gramaticaActualizada.y"
{    
                                                String nombreFun = ((Token)val_peek(2).obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                               }
break;
case 35:
//#line 205 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun = ((Token)val_peek(5).obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun)){           
                                                    Token funcion2 = new Token(nombreFun);
                                                    lexico.getTS().setDeclaracion(nombreFun,funcion2);
                                                    }
                                          }
break;
case 36:
//#line 211 "gramaticaActualizada.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta la primer llave");}
break;
case 37:
//#line 218 "gramaticaActualizada.y"
{    
                                                String nombreFun = ((Token)val_peek(2).obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun2 = ((Token)val_peek(2).obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun2)){           
                                                    Token funcion2 = new Token(nombreFun2);
                                                    lexico.getTS().setDeclaracion(nombreFun2,funcion2);}
                                                
                            }
break;
case 38:
//#line 230 "gramaticaActualizada.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea " + lexico.getFuente().getLinea() +"falta la segunda llave");}
break;
case 39:
//#line 231 "gramaticaActualizada.y"
{    
                                                String nombreFun = ((Token)val_peek(0).obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun2 = ((Token)val_peek(0).obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun2)){           
                                                    Token funcion2 = new Token(nombreFun2);
                                                    lexico.getTS().setDeclaracion(nombreFun2,funcion2);}}
break;
case 40:
//#line 241 "gramaticaActualizada.y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta el primer parentesis");}
break;
case 41:
//#line 242 "gramaticaActualizada.y"
{    
                                                String nombreFun = ((Token)val_peek(1).obj).getId();    
                                                ambito.add(nombreFun);
                                                Token funcion = new Token (nombreFun);
                                                Terceto terceto = new Terceto ("FUNC_clousure", funcion,null);
                                                ctrlTercetos.agregarTerceto(terceto);
                                                lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una funcion VOID");
                                                 String nombreFun2 = ((Token)val_peek(1).obj).getId();
                                                 if(!lexico.getTS().fueDeclarada(nombreFun2)){           
                                                    Token funcion2 = new Token(nombreFun2);
                                                    lexico.getTS().setDeclaracion(nombreFun2,funcion);} {lexico.getLexico().agregarError("error en la creacion del clousure void"+" en la linea "+lexico.getFuente().getLinea() +"falta el segundo parentesis");}
                                                 }
break;
case 42:
//#line 261 "gramaticaActualizada.y"
{ System.out.println(" lei un uslinteger ");}
break;
case 43:
//#line 262 "gramaticaActualizada.y"
{ yyval = new ParserVal ( new Token("USLINTEGER"));
                    }
break;
case 44:
//#line 264 "gramaticaActualizada.y"
{ System.out.println(" lei un double" );}
break;
case 45:
//#line 265 "gramaticaActualizada.y"
{ yyval = new ParserVal ( new Token("DOUBLE"));}
break;
case 50:
//#line 276 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se hizo un print");
                               
                               Token t = new Token (((Token)val_peek(1).obj).getId()+"'");/*ACA NO ME ANDA EL SVAL*/
                               Terceto terceto = new Terceto("Print",t,new Token("-"));
                               t.setTipo(lexico.CADENA);
                               lexico.getTS().setDeclaracion(t.getId(),t);
                               ctrlTercetos.agregarTerceto(terceto);
                                }
break;
case 51:
//#line 284 "gramaticaActualizada.y"
{           /* ESTO NO VA ACA VA EN LOS ERRORES*/
                               Token t = new Token ("error del print");/*ACA NO ME ANDA EL SVAL*/
                               Terceto terceto = new Terceto("Print",t,new Token("-"));
                               ctrlTercetos.agregarTerceto(terceto);
                               
                               
                    }
break;
case 52:
//#line 295 "gramaticaActualizada.y"
{lexico.getLexico().agregarError( "en la linea "+lexico.getFuente().getLinea() +" falta el '(' ");}
break;
case 53:
//#line 296 "gramaticaActualizada.y"
{lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta el ')'");}
break;
case 54:
//#line 297 "gramaticaActualizada.y"
{lexico.getLexico().agregarError( " en la linea "+lexico.getFuente().getLinea() +" falta los '(' ')' ");}
break;
case 55:
//#line 301 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una case");
                                                  ctrlTercetos.desapilarPilaVariablesCase();
                                                 }
break;
case 57:
//#line 307 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta llave inicio ");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
break;
case 58:
//#line 309 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta llave cierre");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
break;
case 59:
//#line 312 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis y llave");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
break;
case 60:
//#line 314 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan llaves ");
                                                            ctrlTercetos.desapilarPilaVariablesCase();}
break;
case 61:
//#line 318 "gramaticaActualizada.y"
{ Terceto terceto = new Terceto("Encabezado_case",(Token)val_peek(1).obj,new Token("-"));
                                       ctrlTercetos.agregarTerceto(terceto); 
                                       ctrlTercetos.apilarPilaCase(terceto);   
                                       
                                     }
break;
case 62:
//#line 323 "gramaticaActualizada.y"
{ Terceto terceto = new Terceto("Encabezado_case",(Token)val_peek(1).obj,new Token("-"));
                                       ctrlTercetos.agregarTerceto(terceto); 
                                       ctrlTercetos.apilarPilaCase(terceto);
                                       lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis ");
                                     }
break;
case 63:
//#line 328 "gramaticaActualizada.y"
{ Terceto terceto = new Terceto("Encabezado_case",(Token)val_peek(0).obj,new Token("-"));
                                       ctrlTercetos.agregarTerceto(terceto); 
                                       ctrlTercetos.apilarPilaCase(terceto);  
                                        lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta parentesis "); 
                                     }
break;
case 65:
//#line 337 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una accion");}
break;
case 66:
//#line 341 "gramaticaActualizada.y"
{ 
                    Token comparacion = ctrlTercetos.getTercetoPilaCase().getToken(1);
                    Terceto terceto = new Terceto("=",(Token)val_peek(2).obj,comparacion);
                    ctrlTercetos.agregarTerceto(terceto);
                    /*//////// creo el terceto con el bf y lo meto en la pila para cuando termine el bloque saber a donde saltar*/
                    Terceto terc = new Terceto("bf_case",new Token(String.valueOf(ctrlTercetos.getTercetos().size()-1)),null);
                    ctrlTercetos.agregarTerceto(terc);
                    ctrlTercetos.setSaltoCase(terc);
                    /*/////////*/
                   
                    if(!comparacion.getTipoReal().equals(((Token)val_peek(2).obj).getTipoReal())){
                        {lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos son incompatibles ");}
                            }
                    }
break;
case 67:
//#line 355 "gramaticaActualizada.y"
{Terceto terceto = new Terceto("bi_case",null,null);
                         ctrlTercetos.agregarTerceto(terceto); 
                         ctrlTercetos.apilarPilaVariablesCase(terceto);
                        ctrlTercetos.getSaltoCase();
                        }
break;
case 68:
//#line 360 "gramaticaActualizada.y"
{}
break;
case 69:
//#line 363 "gramaticaActualizada.y"
{Token comparacion = ctrlTercetos.getTercetoPilaCase().getToken(1);
                    Terceto terceto = new Terceto("=",(Token)val_peek(2).obj,comparacion);
                    ctrlTercetos.agregarTerceto(terceto);
                    /*//////// creo el terceto con el bf y lo meto en la pila para cuando termine el bloque saber a donde saltar*/
                    Terceto terc = new Terceto("bf_case",new Token(String.valueOf(ctrlTercetos.getTercetos().size()-1)),null);
                    ctrlTercetos.agregarTerceto(terc);
                    ctrlTercetos.setSaltoCase(terc);
                    lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el : ");
    }
break;
case 70:
//#line 373 "gramaticaActualizada.y"
{        Terceto terceto = new Terceto("bi_case",null,null);
                        ctrlTercetos.agregarTerceto(terceto); 
                        ctrlTercetos.apilarPilaVariablesCase(terceto);
                        ctrlTercetos.getSaltoCase();
    }
break;
case 71:
//#line 390 "gramaticaActualizada.y"
{System.out.println("leida DOUBLE");
             Token nuevo = (Token) val_peek(0).obj;
             lexico.getTS().setDeclaracion(nuevo.getId(),nuevo);
             nuevo.setTipoReal("double");
             yyval = new ParserVal(nuevo);
             }
break;
case 72:
//#line 397 "gramaticaActualizada.y"
{ System.out.println("Leida CTE");
                        Token nuevo = (Token) val_peek(0).obj;
                        
                        lexico.getTS().setDeclaracion(nuevo.getId(),nuevo);
                        nuevo.setTipoReal("uslinteger");  
                        yyval = new ParserVal(nuevo);
                        }
break;
case 73:
//#line 404 "gramaticaActualizada.y"
{ System.out.println("Leida CTE jorgeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                            Token nuevo = (Token) val_peek(0).obj;
                            nuevo.setNegativo(true);
                            System.out.println(nuevo.getId()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            nuevo.setLexema("-"+nuevo.getId());
                            
                        lexico.getTS().setDeclaracion(nuevo.getId(),nuevo);
                        nuevo.setTipoReal("uslinteger");  
                        yyval = new ParserVal(nuevo);
                        }
break;
case 74:
//#line 418 "gramaticaActualizada.y"
{
                                     Terceto terceto = new Terceto("bf",(Token) val_peek(1).obj,null);
                                     ctrlTercetos.agregarTerceto(terceto);
                                     ctrlTercetos.apilar(terceto.getNro());
                                    }
break;
case 75:
//#line 423 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el '(' de la condicion ");
                                        Terceto terceto = new Terceto("bf",(Token) val_peek(1).obj,null);
                                          ctrlTercetos.agregarTerceto(terceto);
                                          ctrlTercetos.apilar(terceto.getNro());
                                           }
break;
case 76:
//#line 428 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ')' de la condicion ");
                                        Terceto terceto = new Terceto("bf",(Token) val_peek(1).obj,null);
                                        ctrlTercetos.agregarTerceto(terceto);
                                        ctrlTercetos.apilar(terceto.getNro());
                                        }
break;
case 77:
//#line 433 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan los parentesis de la condicion");
                                             Terceto terceto = new Terceto("bf",(Token) val_peek(1).obj,null);
                                           ctrlTercetos.agregarTerceto(terceto);
                                           ctrlTercetos.apilar(terceto.getNro());
                                           }
break;
case 78:
//#line 440 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF");
                                                   ctrlTercetos.desapilar();  
                                                  }
break;
case 79:
//#line 443 "gramaticaActualizada.y"
{ Terceto terceto = new Terceto("bi",null,null);
                                      ctrlTercetos.agregarTerceto(terceto);
                                      ctrlTercetos.desapilar();
                                      ctrlTercetos.apilar(terceto.getNro());

                                    }
break;
case 80:
//#line 448 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una condicion IF con ELSE");
                                                     ctrlTercetos.desapilar();
                                                    }
break;
case 81:
//#line 451 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan el bloque de la condicion");
                                                      ctrlTercetos.desapilar();
                                                     /*MENSAJE ERROR */
                                                    }
break;
case 82:
//#line 456 "gramaticaActualizada.y"
{lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" faltan end_if");
                                                     ctrlTercetos.desapilar();
                                                   /*MENSAJE ERROR */
                                                    }
break;
case 83:
//#line 464 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( "en la linea "+lexico.getFuente().getLinea()+" se agrego una asignacion");System.out.println("realice una asignacion");}
break;
case 84:
//#line 465 "gramaticaActualizada.y"
{ 
                   Token id = (Token) val_peek(3).obj;
                   Token exp = (Token) val_peek(1).obj;
                   Terceto terceto;
                   terceto = new Terceto(":=",id,exp);
                   ctrlTercetos.agregarTerceto(terceto);
                   System.out.println(id.getId()+" idddddddddddddddddddddddddddddddddddddddddddddddddddd"+lexico.getTS().fueDeclarada(id.getId()));
                   System.out.println(exp.getId()+" idddddddddddddddddddddddddddddddddddddddddddddddddddd"+lexico.getTS().fueDeclarada(exp.getId()));
                   
                   if((lexico.getTS().fueDeclarada(id.getId())&&(lexico.getTS().fueDeclarada(exp.getId())))){
                        if( id.getTipoReal().equals(exp.getTipoReal())){ 
                            /*terceto = new Terceto(":=",id,exp);*/
                           }
                         else{
                              /*terceto = new Terceto (":=",id,exp);*/
                             lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos de la asignacion son incompatibles "+id.getTipoReal()+exp.getTipoReal());
                             }
                           /*ctrlTercetos.agregarTerceto(terceto);  */
                    }else{
                        /*terceto= new Terceto(":=",id,exp);*/
                        Character c= id.getId().charAt(0);
                        Character cc= exp.getId().charAt(0);
                        if(!(( Character.isDigit(c)) || (Character.isDigit(cc))))
                            lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +"alguna de las variables no fue declarada");
                        /*System.out.println(id.getId()+"HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");*/
                        /*ctrlTercetos.agregarTerceto(terceto);*/
                         }
                  
                    }
break;
case 86:
//#line 498 "gramaticaActualizada.y"
{ System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el := de la asignacion ");
                                 Token id = (Token) val_peek(1).obj;
                                 Token exp = (Token) val_peek(0).obj;
                                 Terceto terceto;
                                 if( id.getTipoReal().equals(exp.getTipoReal())){
                                     terceto = new Terceto(":=",id,exp);
                                 }
                                  else{ lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos de la asignacion son incompatibles ");
                                        terceto = new Terceto (":=",id,exp);}
                                     
                                 ctrlTercetos.agregarTerceto(terceto);  
                                }
break;
case 87:
//#line 510 "gramaticaActualizada.y"
{ System.out.println("Error"); lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" falta el ID de la asignacion ");
                                          Token id = null;
                                          Token exp = (Token) val_peek(0).obj;
                                          System.out.println(" en la linea "+lexico.getFuente().getLinea()+"no hay id no puedo generar bien el terceto");
                                          lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" los tipos de la asignacion son incompatibles ");
                                          Terceto terceto = new Terceto(":=",id,exp);
                                          ctrlTercetos.agregarTerceto(terceto);  
                 }
break;
case 88:
//#line 523 "gramaticaActualizada.y"
{System.out.println("se hizo una suma ");}
break;
case 89:
//#line 523 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una suma");
                                    Token termino1 = (Token) val_peek(3).obj;
                                        Token factor = (Token) val_peek(1).obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                            String simbolo = "+";
                        
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            System.out.println("estoy imprimiendo el tipo del resultado"+ resultado.getTipoReal());
                                            yyval = new ParserVal(resultado);
                                       }
break;
case 90:
//#line 544 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una resta");
                                        Token termino1 = (Token) val_peek(2).obj;
                                        
                                        Token factor = (Token) val_peek(0).obj;
                                        
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                        String simbolo = "-" ;     
                 
                                        Terceto terceto = new Terceto(simbolo,termino1,factor);
                                         
                                        ctrlTercetos.agregarTerceto(terceto);
                                        Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                        resultado.setTipoReal(factor.getTipoReal());

                                        yyval = new ParserVal(resultado);
                                        
                                       
                                  /*$$= null;    */
                                 }
break;
case 91:
//#line 570 "gramaticaActualizada.y"
{System.out.println("TERMINO a EXPR");
                     yyval = new ParserVal ( (Token) val_peek(0).obj);
                    }
break;
case 92:
//#line 575 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una multiplicacion");
                                        Token termino1 = (Token) val_peek(2).obj;
                                        Token factor = (Token) val_peek(0).obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                            String simbolo = "*";
                                            
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            System.out.println("estoy imprimiendo el tipo del resultado"+ resultado.getTipoReal());
                                            yyval = new ParserVal(resultado);
                                
                                    
                                
                             }
break;
case 93:
//#line 597 "gramaticaActualizada.y"
{lexico.getLexico().agregarEstructura( " en la linea "+lexico.getFuente().getLinea() +" se agrego una division");
                              Token termino1 = (Token) val_peek(2).obj;
                                        Token factor = (Token) val_peek(0).obj;
                                        String tipo = "incompatibles"; 
                                        if(termino1.getTipoReal().equals(factor.getTipoReal())){
                                                 tipo = termino1.getTipoReal();
                                            }
                                        else{
                                            System.out.println("ERROR, TIPOS INCOMPATIBLES"); 
                                            }
                                            String simbolo = "/";
                                             tipo = termino1.getTipoReal();
                                            Terceto terceto = new Terceto(simbolo,termino1,factor);
                                            ctrlTercetos.agregarTerceto(terceto);
                                            Token resultado = new Token(true,String.valueOf(terceto.getNro()));
                                            resultado.setTipoReal(factor.getTipoReal());
                                            yyval = new ParserVal(resultado);
                                }
break;
case 94:
//#line 616 "gramaticaActualizada.y"
{ System.out.println("FACTOR a TERMINO"); 
                    yyval =  new ParserVal((Token) val_peek(0).obj);
                 }
break;
case 95:
//#line 621 "gramaticaActualizada.y"
{ System.out.println("CTE a FACTOR"); }
break;
case 96:
//#line 623 "gramaticaActualizada.y"
{ Token nuevo =(Token) val_peek(0).obj;
                System.out.println(nuevo.getId()+"ESTE ES EL LEXEMA QUE VOY A BUSCAR EN LA TABLA DE SIMBOLOS");
                  
                if(lexico.getTS().fueDeclarada((nuevo.getId())))
                    yyval = new ParserVal(lexico.getTS().getToken(((nuevo.getId()))));
               else
                    System.out.println("el id nunca fue declarado");
             }
break;
case 97:
//#line 651 "gramaticaActualizada.y"
{ Terceto t = new Terceto("if",null,null);
                                              String simbolo = ((Token) val_peek(1).obj).getId(); 
                                              Token exp1 = (Token) val_peek(2).obj;
                                              Token exp2 = (Token) val_peek(0).obj;
                                              if(!exp1.getTipoReal().equals(exp2.getTipoReal())){
                                                    lexico.getLexico().agregarError(" en la linea "+lexico.getFuente().getLinea() +" hay error de tipos ");
                                                    System.out.println(" en la linea "+lexico.getFuente().getLinea()+"los tipos de los datos no son iguales");
                                               }
                                                        
                                              Terceto terceto = new Terceto(simbolo,exp1,exp2);
                                              ctrlTercetos.agregarTerceto(terceto);
                                              Token resultado = new Token(true,String.valueOf(terceto.getNro()));   
                                              resultado.setTipoReal(exp1.getTipoReal());
                                              yyval = new ParserVal(resultado);
                                              
                                            }
break;
case 98:
//#line 669 "gramaticaActualizada.y"
{ String comparador = " <";
                   yyval = new ParserVal(new Token(comparador));
                   }
break;
case 99:
//#line 672 "gramaticaActualizada.y"
{ String comparador = " >";
                   yyval = new ParserVal(new Token(comparador));
                   }
break;
case 100:
//#line 675 "gramaticaActualizada.y"
{ String comparador = "=";
                   yyval = new ParserVal(new Token(comparador));
                   }
break;
case 101:
//#line 678 "gramaticaActualizada.y"
{
                                yyval = new ParserVal(new Token(">="));
                                }
break;
case 102:
//#line 681 "gramaticaActualizada.y"
{
                                yyval = new ParserVal(new Token("<="));
                                }
break;
case 103:
//#line 684 "gramaticaActualizada.y"
{
                                yyval = new ParserVal(new Token("!="));
                                }
break;
//#line 1388 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
